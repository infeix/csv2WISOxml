import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.io.*; 

public class Main  {
  public static final int FAHRTART = 0;
  public static final int TYP = 1;
  public static final int ABFAHRT_ZEITPUNKT = 2;
  public static final int ANKUNFT_ZEITPUNKT = 3;
  public static final int ABFAHRT_KM = 6;
  public static final int ABFAHRT_STRASSE = 7;
  public static final int ABFAHRT_NUMMER = 8;
  public static final int ABFAHRT_PLZ = 9;
  public static final int ABFAHRT_STADT = 10;
  public static final int ANKUNFT_KM = 13;
  public static final int ANKUNFT_STRASSE = 14;
  public static final int ANKUNFT_NUMMER = 15;
  public static final int ANKUNFT_PLZ = 16;
  public static final int ANKUNFT_STADT = 17;
  public static final int KENNZEICHEN = 21;

  public static void write(BufferedWriter writer, boolean use_writer,String arg1) throws IOException
  {
    if(use_writer)
    {
      writer.write(arg1);
    }
    System.out.print(arg1);
  }

  public static void main(String[] args) 
  {
    String splited_fahrt[];
    FileWriter fw;
    BufferedWriter writer;
    BufferedReader br;
    File file;
    String buffer = "";
    String fahrer = "";
    String fahrtart = "";
    Date now = new Date();
    String pattern1 = "yyy-MM-dd";
    String pattern2 = "HH:mm:ss";
    String st;
    boolean erste = true;
    String fahrt_csv;
    SimpleDateFormat datum_frmat = new SimpleDateFormat(pattern1);
    SimpleDateFormat time_format = new SimpleDateFormat(pattern2);
    String export_date = datum_frmat.format(now) + "T" + time_format.format(now);

    if(args.length > 0 && args[0] != null)
    {
      fahrer = args[0];
    }
    else
    {
      fahrer = "Unbekannter Fahrer";
    }

    try
    {
      fw =  new FileWriter("test.xml");
      writer = new BufferedWriter(fw);


      // HEADER
      write(writer,true, "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n");
      write(writer,true, "<root>\r\n");
      write(writer,true, "<ExportInfo>\r\n");
      write(writer,true, "  <ExportDatum>" + export_date + "</ExportDatum>\r\n");
      write(writer,true, "</ExportInfo>\r\n");

      file = new File("test.txt");

      br = new BufferedReader(new FileReader(file));

      while ((fahrt_csv = br.readLine()) != null)
      {
        splited_fahrt = fahrt_csv.split(";", 0);

        try
        {
          if(!splited_fahrt[TYP].equals("Fahrt"))
          {
            throw new ParseException("Is no 'Fahrt'", 0);
          }

          // FAHRT
          buffer = "";
          buffer += "<Fahrt>\r\n";
          buffer += "  <kennzeichen>" + splited_fahrt[KENNZEICHEN] + "</kennzeichen>\r\n";
          buffer += "  <fahrer>" + fahrer.replace("_"," ") + "</fahrer>\r\n";
          buffer += "  <fahrtart>" + splited_fahrt[FAHRTART] + "</fahrtart>\r\n";
          buffer += "  <abfahrtort>" + splited_fahrt[ABFAHRT_STRASSE] + " " + splited_fahrt[ABFAHRT_NUMMER] + "," + splited_fahrt[ABFAHRT_PLZ] + " " + splited_fahrt[ABFAHRT_PLZ] + "</abfahrtort>\r\n";

          Date abfahrt_zeit = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(splited_fahrt[ABFAHRT_ZEITPUNKT]);
          buffer += "  <abfahrt_zeitpunkt>" + datum_frmat.format(abfahrt_zeit) + "T" + time_format.format(abfahrt_zeit) + "</abfahrt_zeitpunkt>\r\n";

          double abfahrt_km =  Double.parseDouble(splited_fahrt[ABFAHRT_KM].replace(",","."));
          buffer += "  <abfahrt_kmstand>" + abfahrt_km + "</abfahrt_kmstand>\r\n";
          buffer += "  <zielort>" + splited_fahrt[ANKUNFT_STRASSE] + " " + splited_fahrt[ANKUNFT_NUMMER] + "," + splited_fahrt[ANKUNFT_PLZ] + " " + splited_fahrt[ANKUNFT_PLZ] + "</zielort>\r\n";

          Date ankunft_zeitpunkt = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(splited_fahrt[ANKUNFT_ZEITPUNKT]);
          buffer += "  <ankunft_zeitpunkt>" + datum_frmat.format(ankunft_zeitpunkt) + "T" + time_format.format(ankunft_zeitpunkt) + "</ankunft_zeitpunkt>\r\n";

          double ankunft_km =  Double.parseDouble(splited_fahrt[ANKUNFT_KM].replace(",","."));
          if(ankunft_km < abfahrt_km)
          {
            throw new ParseException("ankunft_km < abfahrt_km", 0);
          }
          buffer += "  <ankunft_kmstand>" + ankunft_km + "</ankunft_kmstand>\r\n";
          buffer += "</Fahrt>\r\n";

          write(writer, true, buffer);
        }
        catch(ParseException e)
        {
          //
        }
      }

      write(writer,true, "</root>\r\n");
      writer.close();
      fw.close();
    }
    catch(FileNotFoundException e)
    {
      System.out.print("FileNotFoundException");
    }
    catch(IOException e)
    {
      System.out.print("IOException");
    }
  }
}