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

    
    //Este es el contructor de la clase la cargo con un calendar para iniciar la fecha en el Date del sistema 
    //es decir ahora y cargo un metodo init y añado la clase como listener de los eventos del mouse
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

    //clase para añadir a esta clase como escuchador del los click, con la clase Point guardo cuando presiono y cuando suelto, si hay 
    //movimiento pues genera el evento
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

    //Aqui cargo el calendario
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

    //Aqui cargo un arrayList con las fechas de entrenos y para luego compararlas luego y pintar de verde el boton si coinciden
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

    //Aqui paso una variable tipo Date a String para luego manejarlo mejor
    private String dateAString(Date d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(d);
    }

    //Añado los botones y la label para poner el Id del entrenador y lanzo un evento que limitará los datos que se introducen a solo números
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
    
    
    //añado el boton cargar entrenos que consulatara a la base de datos mediante la clase DataAccess, cargo el arrayList listaTotalEntrenos con los entrenos
    //segun el id del entrenador introducido
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

                            // Recorre cada usuario y obtiene sus entrenamientos
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

    

    //Añado mes y año en un label
    private void añadirEncabezado() {
        JLabel encabezado = new JLabel(NOMBRES_MESES[mesActual - 1] + " " + añoActual, SwingConstants.CENTER);
        encabezado.setFont(new Font("Arial", Font.BOLD, 16));
        encabezado.setBorder(border);
        this.add(encabezado, "span 7, center");
    }

    //Añado los ddias de la semana
    private void añadirBotonesSemana() {
        for (String dia : DIAS_SEMANA) {
            JButton boton = new JButton(dia);
            boton.setEnabled(false);
            boton.setBackground(Color.LIGHT_GRAY);
            this.add(boton);
        }
    }

    //añado los espacion al inicio segun que mes es
    private void añadirEspaciosInicio() {
        int espacios = diaInicioMes(mesActual, añoActual);
        for (int i = 0; i < espacios; i++) {
            this.add(new JLabel("")); // Espacios vacíos
        }
    }

    //Año los botones segun los dias tiene el mes, este ha sido el metodo mas dificil.    
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

                //La fecha la debo formatear pasandola de una formna que se pueda cxomparar con la que recoge de la bbdd
                String fechaAComparar = generarFecha(dia, mesActual, añoActual);

                System.out.println("Fecha en efu: " + w.getForDate());
                System.out.println("Fecha a comparar: " + fechaAComparar);

                if (dateAString(w.getForDate()).equals(fechaAComparar)) {
                    botonDia.setBackground(colorBoton);
                    botonDia.repaint();
                }
            }

            //El actionperformerd del boton creado por cada dia genera un ArrayList con los entrenos y el ide del dia y el comentaio y lo pasa con el evento lanzado
            //el evento es personalizado 
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

    //esto evita errore en fase de ejecucion si se introduce en el campo id un string, solo acepta numeros
    private void txtIdEntrenadorKeyTyped(java.awt.event.KeyEvent evt) {
        //limito la entrada por teclado a estos caracteres
        char c = evt.getKeyChar();
        if ((c < '0' || c > '9')) {
            evt.consume();
        }
    }

    //Carga los entrenos del dia como String para luego compararlo
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

    //formatea la fecha para que se pueda comparar , hay que añadir un 0 si el dia es menor de 10 ya que el date te lo devuelve son 0
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

    //es un contador de los entrenos por dia para el tooltip del boton del dia
    private int contadorEntrenosDia(ArrayList<Workout> listaEntrenosUsuarios, String fecha) {
        int coincidencias = 0;

        for (Workout w : listaEntrenosUsuarios) {

            if (dateAString(w.getForDate()).equals(fecha)) {
                coincidencias++;
            }
        }

        return coincidencias;
    }

    //este calcula que dia de la semana empieza el mes
    private int diaInicioMes(int mes, int año) {
        Calendar calendar = new GregorianCalendar(año, mes - 1, 1);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return (diaSemana + 5) % 7;
    }

    private int obtenerDiasDelMes(int mes, int año) {
        Calendar calendar = new GregorianCalendar(año, mes - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //este metodo cambia el mes actual al cambiar de mes con los arrastres, si es diciembre lo pasa a enero y al reves
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

    //devuelkve la lista de usuarios por entrenador
    private ArrayList<Usuari> dameListaUsuariosPorEntrenador(int idEntrenador) {
        return DataAccess.getAllUsersByInstructor(idEntrenador);
    }

    int idEntrenador;

    //devuelve los id de usuarios por entrenador
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
    
    //este es el diparador del evento ara pasar al jlist el arraylist con los entrenos por dia
    public void dispararEventoEnviarArrayEntrenosFechasUsuarios(MiEventoDiaSeleccionado e) {
        if (recogerEntrenos != null) { // Verifica si el listener está configurado

            recogerEntrenos.recogerArrayEntrenos(e); // Llama al método del listener
        }
    }
}
