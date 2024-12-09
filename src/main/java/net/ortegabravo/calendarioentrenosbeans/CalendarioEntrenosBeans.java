
package net.ortegabravo.calendarioentrenosbeans;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class CalendarioEntrenosBeans extends JPanel implements Serializable {


   
    private Color colorBoton;
    private int diaActual;
    private int mesActual;
    private int añoActual;
    private boolean isPresionado;
    private Point clickPresionado;
    private ArrastreListener arrastreListener;

    
    private static final String[] DIAS_SEMANA = {"L", "M", "X", "J", "V", "S", "D"};
    private static final String[] NOMBRES_MESES = {
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

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
        añadirEncabezado();
        añadirBotonesSemana();
        añadirEspaciosInicio();
        añadirDiasDelMes();
        this.revalidate();
        this.repaint();
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
            botonDia.setBackground(colorBoton != null ? colorBoton : Color.WHITE);

            botonDia.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Día seleccionado: " + diaSeleccionado);
                }
            });

            this.add(botonDia);
        }
    }

    private int diaInicioMes(int mes, int año) {
        Calendar calendar = new GregorianCalendar(año, mes - 1, 1);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        return (diaSemana + 5) % 7; // Ajustar para que Lunes sea el primer día
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

    public void setColorBoton(Color colorBoton) {
        this.colorBoton = colorBoton;
        refrescarCalendario();
    }

    public Color getColorBoton() {
        return colorBoton;
    }

    public void addArrastreListener(ArrastreListener listener) {
        this.arrastreListener = listener;
    }

    public void removeArrastreListener() {
        this.arrastreListener = null;
    }
}
