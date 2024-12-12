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
import net.ortegabravo.modelo.EntrenosFechasUsuarios;
import net.ortegabravo.modelo.Usuari;
import net.ortegabravo.modelo.Workout;

public class CalendarioEntrenosBeans extends JPanel implements Serializable {

    private int mes;
    private int anio;
    private Color colorBoton = Color.GREEN;
    private final int diaActual;
    private int mesActual;
    private int añoActual;
    String diaActualconCeroDelante;
    private boolean isPresionado;
    private Point clickPresionado;
    private ArrastreListener arrastreListener;
    String fechaFormateada;
   
    JTextField txtIdEntrenador;
    ArrayList<Workout> listaTotalEntrenos = new ArrayList<>();
    ArrayList<String> listaEntrenosTotalesString = new ArrayList<>();
    private ArrayList<EntrenosFechasUsuarios> arrayEntrenosUsuarios = new ArrayList<>();
    EntrenosFechasUsuarios efu = new EntrenosFechasUsuarios();

    private static final String[] DIAS_SEMANA = {"L", "M", "X", "J", "V", "S", "D"};
    private static final String[] NOMBRES_MESES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
    String fechaAComparar = null;
    private ArrayList<EntrenosFechasUsuarios> fechasFiltradas;
    int mensaje = 0;

    public CalendarioEntrenosBeans() {
        Calendar calendar = Calendar.getInstance();
        diaActual = calendar.get(Calendar.DAY_OF_MONTH);
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
                            cambiarMes(1); // Avanzar mes
                        } else {
                            cambiarMes(-1); // Retroceder mes
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
        //recorrer el list con los entrenos

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Formato: día/mes/año
        for (Workout w : listaTotalEntrenos) {

            listaEntrenosTotalesString.add(dateAString(w.getForDate())); //Esto pasa el date del array a string para comparar luego
        }
        //comparar con el dia mes y año
        //si coincide pinta el buton de verde

        System.out.println("lista de entrenos string cargada ahora recorro el list");
        System.out.println("El tamaño del array Listaentrenostotales es------->" + listaEntrenosTotalesString.size());

        return listaEntrenosTotalesString;
    }

    private String dateAString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    private void añadirBotonIDEntrenador() {
        txtIdEntrenador = new JTextField("aqui id entrenador");
        this.add(txtIdEntrenador, "span 7, growx");

    }

    private void añadirbotonCargarEntrenos() {
        JButton boton = new JButton("Cargar entrenos");
        boton.setBackground(Color.CYAN);
        this.add(boton, "span 7,growx, wrap");

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Acción al hacer clic en el botón
                idEntrenador = Integer.parseInt(txtIdEntrenador.getText());
                // Lista de entrenamientos por usuario

                // Obtener lista de usuarios por entrenador (id entrenador = 7)
                ArrayList<Usuari> usuariosPorEntrenador = dameListaUsuariosPorEntrenador(idEntrenador);

                if (usuariosPorEntrenador == null || usuariosPorEntrenador.isEmpty()) {
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

                //EntrenosFechasUsuarios efu = new EntrenosFechasUsuarios();
                // Verificar y mostrar entrenamientos almacenados en la lista total
                if (listaTotalEntrenos.isEmpty()) {
                    System.out.println("No se encontraron entrenamientos para ningún usuario.");
                } else {
                    for (Workout w : listaTotalEntrenos) {

                        System.out.println("Entrenamiento ID: " + w.getId() + " - Fecha: " + w.getForDate());
                        EntrenosFechasUsuarios efu = new EntrenosFechasUsuarios();
                        efu.setId(w.getId());
                        efu.setFechaEntreno(dateAString(w.getForDate()));
                        arrayEntrenosUsuarios.add(efu);
                        System.out.println("este es efu:" + efu.getId() + " " + efu.getFechaEntreno());

                    }// aqui ya tengo arrayEntrenos Usuarios cargado con dos string fecha e id
                }

                System.out.println("estoy dentro de arrayEntrenosUsuarios");
                System.out.println("tamaño" + arrayEntrenosUsuarios.size());
                for (EntrenosFechasUsuarios ef : arrayEntrenosUsuarios) {

                    System.out.println(ef.getFechaEntreno() + "  " + ef.getId());

                }
                //cargarArrayEntrenosUsuarios(arrayEntrenosUsuarios);
                cargaArrayEntrenosTotalesAString();
            }

        });

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
             
             
            fechaFormateada=generarFecha(dia,mesActual, añoActual); 
             System.out.println(fechaFormateada);
             
             mensaje=contadorEntrenosDia(arrayEntrenosUsuarios, fechaFormateada);
             botonDia.setToolTipText(String.valueOf(mensaje));
                  


                
            for (EntrenosFechasUsuarios efu : arrayEntrenosUsuarios) {
                System.out.println(efu.getFechaEntreno() + " id: " + efu.getId() + " y dia vale: " + dia);


                  String fechaAComparar=generarFecha(dia,mesActual, añoActual); 
                  
                 

                System.out.println("Fecha en efu: " + efu.getFechaEntreno());
                System.out.println("Fecha a comparar: " + fechaAComparar);

                if (efu.getFechaEntreno().equals(fechaAComparar)) {
                    botonDia.setBackground(colorBoton);
                    botonDia.repaint();
                }
            }

            botonDia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Día seleccionado: " + diaSeleccionado);

                    // Cargar entrenos del día seleccionado
                    //cargaEntrenosdiaSeleccionado();
                    
                    System.out.println(generarFecha(diaSeleccionado,mesActual,añoActual));
                    
                    //dispararEventoEnviarArrayEntrenosFechasUsuarios(arrayEntrenosUsuarios);
                    
                    dispararEventoEnviarArrayEntrenosFechasUsuarios( cargaEntrenosdiaSeleccionado(generarFecha(diaSeleccionado,mesActual,añoActual)));
                  
                    
                    
                    
                    
                    
                    
                    
                }
            });

            this.add(botonDia); // Agregar el botón al contenedor
        }
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
    
    
    public static int contadorEntrenosDia(ArrayList<EntrenosFechasUsuarios> arrayEntrenosUsuarios, String fecha) {
        int coincidencias = 0;
       
        for (EntrenosFechasUsuarios s : arrayEntrenosUsuarios) {
            
            if (s.getFechaEntreno().equals(fecha)) {
                coincidencias++;
            }
        }

        return coincidencias;
    }
    
    
    private ArrayList<String> cargaEntrenosdiaSeleccionado(String fecha) {
        
        ArrayList<String> fechasFiltradas = new ArrayList<>();
        for (EntrenosFechasUsuarios efu : arrayEntrenosUsuarios) {
            if (efu.getFechaEntreno().equals(fecha)) {
                fechasFiltradas.add(efu.getFechaEntreno());
                System.out.println(fecha);
            }
        }
        return fechasFiltradas;
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
        System.out.println("esta en dame lista");
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

    public ArrayList<EntrenosFechasUsuarios> getArrayEntrenosUsuarios() {
        return arrayEntrenosUsuarios;
    }

    public void setArrayEntrenosUsuarios(ArrayList<EntrenosFechasUsuarios> arrayEntrenosUsuarios) {
        this.arrayEntrenosUsuarios = arrayEntrenosUsuarios;
    }

    public ArrayList<EntrenosFechasUsuarios> getFechasFiltradas() {
        return fechasFiltradas;
    }

    public void setFechasFiltradas(ArrayList<EntrenosFechasUsuarios> fechasFiltradas) {
        this.fechasFiltradas = fechasFiltradas;
    }

    
    public void addArrastreListener(ArrastreListener listener) {
        this.arrastreListener = listener;
    }

    public void removeArrastreListener() {
        this.arrastreListener = null;
    }

    MiEventoInterfaceRecogerArrayEntrenosListener recogerEntrenos;
    
    
     public void addRecogerArrayEntrenosListener(MiEventoInterfaceRecogerArrayEntrenosListener listener) {
        this.recogerEntrenos= listener;
    }

    public void removeRecogerArrayEntrenosListener() {
        this.recogerEntrenos= null;
    }
    
    
//    private void dispararEventoEnviarArrayEntrenosFechasUsuarios(ArrayList<EntrenosFechasUsuarios>efu) {
//        MiEventoDiaSeleccionado evento = new MiEventoDiaSeleccionado(this,efu);
//         listener.recogerArrayEntrenos(evento);
//    }
//    
//    public void dispararEventoEnviarArrayEntrenosFechasUsuarios(ArrayList<EntrenosFechasUsuarios> efu) {
//        if (recogerEntrenos != null) { // Verifica si el listener está configurado
//            //MiEventoDiaSeleccionado evento = new MiEventoDiaSeleccionado(this, efu);
//            recogerEntrenos.recogerArrayEntrenos(efu); // Llama al método del listener
//        }
//    }  
//    
    
     public void dispararEventoEnviarArrayEntrenosFechasUsuarios(ArrayList<String> efu) {
        if (recogerEntrenos != null) { // Verifica si el listener está configurado
            //MiEventoDiaSeleccionado evento = new MiEventoDiaSeleccionado(this, efu);
            recogerEntrenos.recogerArrayEntrenos(efu); // Llama al método del listener
        }
    }  
}
