package rcreativity.locate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by USUARIO-WIN on 01/02/2015.
 */
public class Productos {

//    String csvFile = "csv/micomisariato.csv";
//    BufferedReader br = null;
//    String line = "";
//    String cvsSplitBy = ",";
//
//    static String[] productos;
//    static String[] ubicacion;
//    //static int[] croquis;
//
//    Productos() {
//        int i=0;
//        try {
//
//            br = new BufferedReader(new FileReader(csvFile));
//            while ((line = br.readLine()) != null) {
//
//                // use comma as separator
//                String[] linea = line.split(cvsSplitBy);
//
//                String prod = linea[1];
//                String ubi = linea[2];
//                //croquis[i] = Integer.parseInt("R.drawable."+linea[2]);
//
//                i++;
//
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    static String[] productos = new String[] {
        "productos-de-higiene-personal",
        "ambientales",
        "utiles-para-limpieza",
        "comida-para-perros",
        "comida-para-gatos",
        "desinfectantes",
        "detergentes",
        "cafe-y-chocolate",
        "leche-en-polvo",
        "galletas",
        "gelatinas",
        "fideos",
        "cereales",
        "salsas",
        "atun",
        "mantequilla",
        "aceites",
        "granos",
        "granosb",
        "Pan-y-cake",
        //"accesorios-de-belleza",
        "bebidas-gaseosas",
        "cajas",
        "carnes-y-embutidos",
        "carteras-zapatos",
        "casilleros",
        "cola-helada",
        "Desodorantes-lociones-y-cremas",
        "embutidos",
        "Entrada-y-salida",
        "frutas",
        "helados",
        "higiene-bucal",
        "insectisidas",
        "isla-claro",
        "islas-con-productos-de-oferta",
        "lacteos",
        "legumbres",
        "lencerias-femenina",
        "lencerias-masculina",
        "limpieza-y-belleza-femenina",
        "mapa",
        "panaderia",
        "pañales-y-productos-para-bebes",
        "pavos-y-pollos-congelados",
        "pollo-hornado",
        "productos-congelados",
        "productos-de-cabello",
        "ropa-para-caballeros",
        "ropa-para-damas",
        "ropa-para-jovenes",
        "ropa-para-niñas",
        "ropa-para-niños",
        "ropa-para-señoritas",
        "servicio-al-cliente",
        "textiles-de-hogar",
        "utiles-de-higiene",
        "W-union"
    };

    //Array of integers points to images stored in /res/drawable
    static int[] croquis = new int[]{
            R.drawable.a1,
            R.drawable.a2,
            R.drawable.b1,
            R.drawable.b2,
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.a3,
            R.drawable.a4,
            R.drawable.b3,
            R.drawable.b4,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.a5,
            R.drawable.a6,
            R.drawable.b5,
            R.drawable.b6,
            R.drawable.c5,
            R.drawable.c6,
            R.drawable.a7,
            R.drawable.a8,
            //R.drawable.b7,
            R.drawable.b8,
            R.drawable.c7,
            R.drawable.c8,
            R.drawable.a9,
            R.drawable.a10,
            R.drawable.b9,
            R.drawable.b10,
            R.drawable.c9,
            R.drawable.c10,
            R.drawable.a11,
            R.drawable.a12,
            R.drawable.b11,
            R.drawable.b12,
            R.drawable.c11,
            R.drawable.c12,
            R.drawable.a13,
            R.drawable.a14,
            R.drawable.b13,
            R.drawable.b14,
            R.drawable.c13,
            R.drawable.c14,
            R.drawable.a15,
            R.drawable.a16,
            R.drawable.b15,
            R.drawable.b16,
            R.drawable.c15,
            R.drawable.c16,
            R.drawable.a17,
            R.drawable.a18,
            R.drawable.b17,
            R.drawable.b18,
            R.drawable.c17,
            R.drawable.c18,
            R.drawable.a19,
            R.drawable.a20,
            R.drawable.b19,
            R.drawable.b20
    };

    //Array of strings to store currencies
    static String[] ubicacion = new String[]{
            "a1",
            "a2",
            "b1",
            "b2",
            "c1",
            "c2",
            "a3",
            "a4",
            "b3",
            "b4",
            "c3",
            "c4",
            "a5",
            "a6",
            "b5",
            "b6",
            "c5",
            "c6",
            "a7",
            "a8",
            //"b7",
            "b8",
            "c7",
            "c8",
            "a9",
            "a10",
            "b9",
            "b10",
            "c9",
            "c10",
            "a11",
            "a12",
            "b11",
            "b12",
            "c11",
            "c12",
            "a13",
            "a14",
            "b13",
            "b14",
            "c13",
            "c14",
            "a15",
            "a16",
            "b15",
            "b16",
            "c15",
            "c16",
            "a17",
            "a18",
            "b17",
            "b18",
            "c17",
            "c18",
            "a19",
            "a20",
            "b19",
            "b20"
    };
}
