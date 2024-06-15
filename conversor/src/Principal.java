import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        int opcion = 1;
        int opcionMenu = 0;

        Scanner scanner = new Scanner(System.in);
        while(opcion != 3){
            String Moneda1 = "";
            String Moneda2 = "";
            double monto = 0;

            System.out.println("""
            \n*******************************
            BIENVENIDO AL CONVERSOR DE MONEDAS 
 
            1) CONVERSAR MONEDA
            3) SALIR
            *******************************
            """);
            opcion = scanner.nextInt();
            if(opcion == 3){
                System.out.println("Ha salido de la aplicacion");
                break;
            }
            switch (opcion){
                case 1:
                    //menu
                    System.out.println("""
                *****************************
                ARS - Peso argentino
                                
                BOB - Boliviano boliviano
                                
                BRL - Real brasileño
                                
                CLP - Peso chileno
                                
                COP - Peso colombiano
                                
                USD - Dólar estadounidense
                *****************************
                Digite las iniciales de la moneda base: 
                """);
                    Moneda1 = scanner.next().toUpperCase();

                    System.out.println("""
                *****************************
                ARS - Peso argentino
                                
                BOB - Boliviano boliviano
                                
                BRL - Real brasileño
                                
                CLP - Peso chileno
                                
                COP - Peso colombiano
                                
                USD - Dólar estadounidense
                ***************************** 
                """);
                    System.out.println("Digite las iniciales de la moneda a la que quiere conversar");
                    Moneda2 = scanner.next().toUpperCase();

                    System.out.println("Digite la cantidad "+Moneda1+" que va a convertir -->> "+ Moneda2);
                    monto = Double.parseDouble(String.valueOf(scanner.nextDouble()));
                    break;
            }
            URI direccion = URI.create("https://v6.exchangerate-api.com/v6/205d4a250db33ba28208cbc6/pair/"+Moneda1+"/"+Moneda2+"/"+monto);


            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(direccion)
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                //se convirte a Json
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson((response.body()), JsonObject.class);
                //Despues de haberlo convertido se llama al parametro "conversion_resulta" para asacar el resultado de la
                //De la conversion de la moneda directamente de la consulta de la API
                double Respuesta = jsonObject.get("conversion_result").getAsDouble();

                System.out.println("\n El resultado es de convertir "+monto+" "+ Moneda1 + " es "+ +Math.round(Respuesta*100.0)/100.0 + " "+Moneda2) ;


            } catch (NumberFormatException e){
                System.out.println("ERROR");
            } catch (Exception e){
                System.out.println("ERROR");
            }

        }

    }
}