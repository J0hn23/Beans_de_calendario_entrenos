package net.ortegabravo.calendarioentrenosbeans;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

import net.ortegabravo.modelo.Usuari;
import net.ortegabravo.modelo.Workout;

public class CalendarioEntrenosBeans extends JPanel implements Serializable {

    private int mes;
    private int anio;
    private Color colorBoton = Color.GREEN;
    private int mesActual;
    private int añoActual;
    private boolean isPresionado;
    private Point clickPresionado;
    private ArrastreListener arrastreListener;
    private String fechaFormateada;
    private int mensaje = 0;
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

    JTextField txtIdEntrenador;
    ArrayList<Workout> listaTotalEntrenos = new ArrayList<>();
    ArrayList<String> listaEntrenosTotalesString = new ArrayList<>();

    private static final String[] DIAS_SEMANA = {"L", "M", "X", "J", "V", "S", "D"};
    private static final String[] NOMBRES_MESES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    public CalendarioEntrenosBeans() {
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.DAY_OF_MONTH);
        mesActual = calendar.get(Calendar.MONTH) + 1;
        añoActual = calendar.get(Calendar.YEAR);

        init();
        configurarEventosMouse();
    }

    private void init() {
        this.setLayout(new MigLayout("wrap 7, fill", "[grow,fill]", "[grow,fill]"));
        this.setBorder(border);
        this.setBackground(Color.DARK_GRAY);
        refrescarCalendario();

    }

    private void configurarEventosMouse() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isPresionado = true;
                clickPresionado = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isPresionado) {
                    Point clickSoltado = e.getPoint();
                    if (Math.abs(clickPresionado.y - clickSoltado.y) > 30) {
                        if (clickPresionado.y > clickSoltado.y) {
                            cambiarMes(1); // Avanza mes
                        } else {
                            cambiarMes(-1); // Retrocede mes
                        }
                    }
                    isPresionado = false;
                }
            }
        });
    }

    private void refrescarCalendario() {
        this.removeAll();
        añadirBotonIDEntrenador();
        añadirbotonCargarEntrenos();
        añadirEncabezado();
        añadirBotonesSemana();
        añadirEspaciosInicio();
        añadirDiasDelMes();
        this.revalidate();
        this.repaint();
    }

    private ArrayList<String> cargaArrayEntrenosTotalesAString() {
        //recorrer el list con los entrenos y devuelve listaentrenostotales String con los entrenos

        for (Workout w : listaTotalEntrenos) {

            listaEntrenosTotalesString.add(dateAString(w.getForDate())); //Esto pasa el date del array a string para comparar luego
        }
        //comparar con el dia mes y año
        //si coincide pinta el buton de verde

        System.out.println("lista de entrenos string cargada ahora recorro el list");
        System.out.println("El tamaño del array Listaentrenostotales es------->" + listaEntrenosTotalesString.size());

        return listaEntrenosTotalesString;
    }

    private String dateAString(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(d);
    }

    private void añadirBotonIDEntrenador() {
        JLabel lblId = new javax.swing.JLabel("ID:");
         this.add(lblId, " growx");
        //this.add(lblId, "span 7, growx");
        
        txtIdEntrenador = new JTextField("");
        this.add(txtIdEntrenador, " growx, wrap");
        //this.add(txtIdEntrenador, "span 7, growx");

        txtIdEntrenador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdEntrenadorKeyTyped(evt);
            }
        });

    }

    private void añadirbotonCargarEntrenos() {
        JButton boton = new JButton("Cargar entrenos");
        boton.setBackground(Color.CYAN);
        this.add(boton, "span 7,growx, wrap");

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón

                if (txtIdEntrenador.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,"El campo texto está vacío", "Mensaje", JOptionPane.WARNING_MESSAGE);
                    txtIdEntrenador.requestFocus(); 
                } else {

                        idEntrenador = Integer.parseInt(txtIdEntrenador.getText());
                       

                       
                        ArrayList<Usuari> usuariosPorEntrenador = dameListaUsuariosPorEntrenador(idEntrenador);

                        if (usuariosPorEntrenador == null || usuariosPorEntrenador.isEmpty()) {
                             JOptionPane.showMessageDialog(null,"No existe el usuario", "Mensaje", JOptionPane.WARNING_MESSAGE);
                            System.out.println("No se encontraron usuarios para el entrenador especificado.");
                            return;
                        }

                            // Recorrer cada usuario y obtener sus entrenamientos
                        for (Usuari usuario : usuariosPorEntrenador) {

                            ArrayList<Workout> entrenosUsuario = DataAccess.getWorkoutsPerUser(usuario);

                            if (entrenosUsuario != null && !entrenosUsuario.isEmpty()) {
                                listaTotalEntrenos.addAll(entrenosUsuario);
                                System.out.println("Añadiendo entrenamientos para el usuario: " + usuario.getId() + " - " + usuario.getNom());
                            } else {
                            System.out.println("No se encontraron entrenamientos para el usuario: " + usuario.getId() + " - " + usuario.getNom());
                            }
                        }                    
                        // Muestra entrenamientos almacenados en la lista total
                        if (listaTotalEntrenos.isEmpty()) {
                            System.out.println("No se encontraron entrenamientos para ningún usuario.");
                        }
                        System.out.println("estoy dentro de arrayEntrenosUsuarios");
                        System.out.println("tamaño" + listaTotalEntrenos.size());
                        for (Workout ef : listaTotalEntrenos) {

                            System.out.println(ef.getComments() + "  " + ef.getForDate() + " " + ef.getIdUsuari());

                        }
                       
                        cargaArrayEntrenosTotalesAString();
                    }
                }
            }
        );
    }

    

    private void añadirEncabezado() {
        JLabel encabezado = new JLabel(NOMBRES_MESES[mesActual - 1] + " " + añoActual, SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 16));
        encabezado.setBorder(border);
        this.add(encabezado, "span 7, center");
    }

    private void añadirBotonesSemana() {
        for (String dia : DIAS_SEMANA) {
            JButton boton = new JButton(dia);
            boton.setEnabled(false);
            boton.setBackground(Color.LIGHT_GRAY);
            this.add(boton);
        }
    }

    private void añadirEspaciosInicio() {
        int espacios = diaInicioMes(mesActual, añoActual);
        for (int i = 0; i < espacios; i++) {
            this.add(new JLabel("")); // Espacios vacíos
        }
    }

    private void añadirDiasDelMes() {
        int diasEnMes = obtenerDiasDelMes(mesActual, añoActual);

        for (int dia = 1; dia <= diasEnMes; dia++) {
            final int diaSeleccionado = dia;
            JButton botonDia = new JButton(String.valueOf(dia));
            botonDia.setBackground(Color.WHITE);

            //mensaje debe cargar el numero de entrenos de ese dia metodo buscar entrenos por dia
            fechaFormateada = generarFecha(dia, mesActual, añoActual);
            System.out.println(fechaFormateada);

            mensaje = contadorEntrenosDia(listaTotalEntrenos, fechaFormateada);
            botonDia.setToolTipText(String.valueOf(mensaje));

            for (Workout w : listaTotalEntrenos) {
                System.out.println(w.getComments() + " id: " + w.getId() + " y dia vale: " + dia);

                String fechaAComparar = generarFecha(dia, mesActual, añoActual);

                System.out.println("Fecha en efu: " + w.getForDate());
                System.out.println("Fecha a comparar: " + fechaAComparar);

                if (dateAString(w.getForDate()).equals(fechaAComparar)) {
                    botonDia.setBackground(colorBoton);
                    botonDia.repaint();
                }
            }

            botonDia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Día seleccionado: " + diaSeleccionado);

                    // Carga entrenos del día seleccionado
                    System.out.println(generarFecha(diaSeleccionado, mesActual, añoActual));

                    MiEventoDiaSeleccionado evento = new MiEventoDiaSeleccionado(this, cargaEntrenosdiaSeleccionado(generarFecha(diaSeleccionado, mesActual, añoActual)));

                    dispararEventoEnviarArrayEntrenosFechasUsuarios(evento);

                }
            });

            this.add(botonDia); // Agregar el botón al contenedor
        }
    }

    private void txtIdEntrenadorKeyTyped(java.awt.event.KeyEvent evt) {
        //limito la entrada por teclado a estos caracteres
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }

    private ArrayList<String> cargaEntrenosdiaSeleccionado(String fecha) {

        ArrayList<String> fechasFiltradas = new ArrayList<>();

        for (Workout w : listaTotalEntrenos) {

            if (dateAString(w.getForDate()).equals(fecha)) {
                String cadenaEntrenoDia = "Entreno:" + w.getComments() + " || Usuario:" + String.valueOf(w.getIdUsuari() + " || Fecha:" + w.getForDate());

                fechasFiltradas.add(cadenaEntrenoDia);
                System.out.println(fecha);
            }
        }
        return fechasFiltradas;
    }

    public static String generarFecha(int dia, int mesActual, int añoActual) {
        String diaActualconCeroDelante;

        if (dia < 10) {
            diaActualconCeroDelante = "0" + dia;
        } else {
            diaActualconCeroDelante = String.valueOf(dia);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaAComparar = diaActualconCeroDelante + "/" + mesActual + "/" + añoActual;

        return fechaAComparar;
    }

    private int contadorEntrenosDia(ArrayList<Workout> listaEntrenosUsuarios, String fecha) {
        int coincidencias = 0;

        for (Workout w : listaEntrenosUsuarios) {

            if (dateAString(w.getForDate()).equals(fecha)) {
                coincidencias++;
            }
        }

        return coincidencias;
    }

    private int diaInicioMes(int mes, int año) {
        Calendar calendar = new GregorianCalendar(año, mes - 1, 1);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return (diaSemana + 5) % 7;
    }

    private int obtenerDiasDelMes(int mes, int año) {
        Calendar calendar = new GregorianCalendar(año, mes - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private void cambiarMes(int incremento) {
        mesActual += incremento;
        if (mesActual > 12) {
            mesActual = 1;
            añoActual++;
        } else if (mesActual < 1) {
            mesActual = 12;
            añoActual--;
        }
        if (arrastreListener != null) {
            if (incremento > 0) {
                arrastreListener.arrastreArriba();
            } else {
                arrastreListener.arrastreAbajo();
            }
        }
        refrescarCalendario();
    }

    private ArrayList<Usuari> dameListaUsuariosPorEntrenador(int idEntrenador) {
        ArrayList<Usuari> listaUsuarios = new ArrayList<>();
        return listaUsuarios = DataAccess.getAllUsersByInstructor(idEntrenador);
    }

    int idEntrenador;

    public void extraerUsuariosPorEntrenador() {

        ArrayList<Integer> numeroUsuarioPorEntrenador = new ArrayList<>();

        for (Usuari u : dameListaUsuariosPorEntrenador(idEntrenador)) {

            numeroUsuarioPorEntrenador.add(u.getId());//ya tengo un list con los id usuarios por entrenador

        }

    }

    public void setColorBoton(Color colorBoton) {
        this.colorBoton = colorBoton;
        refrescarCalendario();
    }

    public Color getColorBoton() {
        return colorBoton;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAño(int anio) {
        this.anio = anio;
    }

    public void addArrastreListener(ArrastreListener listener) {
        this.arrastreListener = listener;
    }

    public void removeArrastreListener() {
        this.arrastreListener = null;
    }

    MiEventoInterfaceRecogerArrayEntrenosListener recogerEntrenos;

    public void addRecogerArrayEntrenosListener(MiEventoInterfaceRecogerArrayEntrenosListener listener) {
        this.recogerEntrenos = listener;
    }

    public void removeRecogerArrayEntrenosListener() {
        this.recogerEntrenos = null;
    }

    public void dispararEventoEnviarArrayEntrenosFechasUsuarios(MiEventoDiaSeleccionado e) {
        if (recogerEntrenos != null) { // Verifica si el listener está configurado

            recogerEntrenos.recogerArrayEntrenos(e); // Llama al método del listener
        }
    }
}
