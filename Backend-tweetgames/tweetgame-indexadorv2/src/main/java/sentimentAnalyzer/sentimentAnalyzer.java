package sentimentAnalyzer;

import model.tweetModel;
import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class sentimentAnalyzer {

    public Set<String> positiveWords = new HashSet<>();
    public Set<String> negativeWords = new HashSet<>();
    public CharArraySet stopwords = new SpanishAnalyzer(Version.LUCENE_46).getStopwordSet();


    public sentimentAnalyzer(){
        //Cargado de la bolsa de palabras con valoraciones
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            positiveWords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("positive.dat"), "UTF-8"));
            negativeWords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("negative.dat"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sAnalizer(String text)
    {
        String positivo = "positivo";
        String negativo = "negativo";
        String neutro = "neutro";

        //Analizador de texto
        String[] st = text.split(" ");

        int cPositive = 0;
        int cNegative = 0;
        for( String tAux: st) {
            text  = StringUtils.stripAccents(tAux.toLowerCase());
            for(String wordP : positiveWords)
            {
                if(text.matches(".*"+wordP+".*")){
                    cPositive++;
                }
            }
            for(String wordN : negativeWords)
            {
                if(text.matches(".*"+wordN+".*")){
                    cNegative++;
                }
            }

        }

        if(cPositive > cNegative){
            return positivo;
        }
        else if(cPositive < cNegative){
            return negativo;
        }
        else{
            return neutro;
        }
    }
}
