import java.net.*;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;




public class crawler {
    private String start_url;

    public void setStart_url(String url){
        this.start_url = url;
    }

    public crawler (String url){
        this.start_url = url;
    }

    public void startCrawling (){
        getData(this.start_url);
       //System.out.println(getHTLM(start_url));
    }

    private void getData (String url){
        String html = getHTLM(url);
        Document doc = Jsoup.parse(html);
        Elements elementsLINKS = doc.select("a");

        for (Element e:elementsLINKS) {
                String href = e.attr("href");
                System.out.println(e);
                href = processLink(href, url);
                System.out.println(href);
        }


    }

    private String processLink(String link, String base) {

        try {
            URL u = new URL(base);

            if (link.startsWith("./")) {
                link = link.substring(2, link.length());
                link = u.getProtocol() + "://" + u.getAuthority() + link;

            }
            else if (link.startsWith("/")) {
                link = link.substring(1, link.length());
                link = u.getProtocol() + "://" + u.getAuthority() +"/"+ link;

            } else if (link.startsWith("#")) {
                link = base + link;

            } else if (link.startsWith("javascript:")) {
                link = null;

            } else if (link.startsWith("../") || (!link.startsWith("http://") && !link.startsWith("https://"))) {
                link = u.getProtocol() + "://" + u.getAuthority() + stripFilename(u.getPath()) + link;

            }
            return link;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    private String stripFilename(String path) {
        int pos = path.lastIndexOf("/");
        return pos <= -1 ? path : path.substring(0, pos+1);
    }

    public  String getHTLM (String url){
        URL link;
        try{
            link = new URL(url);
            URLConnection connection = link.openConnection();
            connection.setRequestProperty("User-Agent","RETRIEVAL PROJECT CRAWLER");
            connection.setRequestProperty("Accept-charset","UTF-8");
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader (new InputStreamReader(is));

            String line;
            String html= "";
            while ((line = reader.readLine())!=null){
                html = html + line +"\n";
            }
            html.trim();
            return  html;

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }






}
