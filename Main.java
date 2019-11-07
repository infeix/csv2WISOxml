import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.io.*; 

public class Main  {
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
    int offset = 1;
    String fahrer = "";
    String fahrtart = "";
    if(args.length > 0 && args[0] != null)
    {
      fahrer = args[0];
    }
    else
    {
      fahrer = "Unbekannt";
    }

    if(args.length > 1 && args[1] != null)
    {
      fahrtart = args[1];
      offset = 0;
    }

    Date now = new Date();
    String pattern1 = "yyy-MM-dd";
    String pattern2 = "HH:mm:ss";
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(pattern1);
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
    String export_date = simpleDateFormat1.format(now) + "T" + simpleDateFormat2.format(now);


    try
    {
      FileWriter fw =  new FileWriter("test.xml");
      BufferedWriter writer = new BufferedWriter(fw);

      write(writer,true, "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n");
      write(writer,true, "<root>\r\n");
      write(writer,true, "<ExportInfo>\r\n");
      write(writer,true, "  <ExportDatum>" + export_date + "</ExportDatum>\r\n");
      write(writer,true, "</ExportInfo>\r\n");

      File file = new File("test.txt"); 
      
      BufferedReader br = new BufferedReader(new FileReader(file));
      
      String st;
      boolean erste = true;
      String myString;
      while ((myString = br.readLine()) != null) 
      {
        String s[] = myString.split(";", 0);
        
        try
        {
          if(!s[offset + 0].equals("Fahrt"))
          {
            throw new ParseException("Is no 'Fahrt'", 0);
          }
          write(writer,true, "<Fahrt>\r\n");
          write(writer,true, "  <kennzeichen>" + s[offset + 20] + "</kennzeichen>\r\n");
          write(writer,true, "  <fahrer>" + fahrer + "</fahrer>\r\n");
          if((offset + -1) >= 0)
          {
            fahrtart = s[offset + -1];
          }
          write(writer,true, "  <fahrtart>" + fahrtart + "</fahrtart>\r\n");
          write(writer,true, "  <route>" + s[offset + 9] + "-" + s[offset + 16] + "</route>\r\n");
          write(writer,true, "  <abfahrtort>" + s[offset + 6] + " " + s[offset + 7] + "," + s[offset + 8] + " " + s[offset + 9] + "</abfahrtort>\r\n");
          
          Date date1 = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(s[offset + 1]);  
          write(writer,true, "  <abfahrt_zeitpunkt>" +
            simpleDateFormat1.format(date1) + "T" +
            simpleDateFormat2.format(date1) +
            "</abfahrt_zeitpunkt>\r\n");
          write(writer,true, "  <abfahrt_kmstand>" + s[offset + 5].replace(",",".") + "</abfahrt_kmstand>\r\n");
          write(writer,true, "  <zielort>" + s[offset + 13] + " " + s[offset + 14] + "," + s[offset + 15] + " " + s[offset + 16] + "</zielort>\r\n");
          Date date2 = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(s[offset + 2]);  
          write(writer,true, "  <ankunft_zeitpunkt>" + 
            simpleDateFormat1.format(date2) + "T" +
            simpleDateFormat2.format(date2) + 
            "</ankunft_zeitpunkt>\r\n");
          write(writer,true, "  <ankunft_kmstand>" + s[offset + 12].replace(",",".") + "</ankunft_kmstand>\r\n");
          //for (int i=0; i<s.length; i++) 
          //{
          //   String element = s[offset + i];
          //   write(writer,true, i + "<kennzeichen>" + element + "</kennzeichen>");
          //}
          write(writer,true, "</Fahrt>\r\n"); 

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