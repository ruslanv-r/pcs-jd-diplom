import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    File pdfsDir;
    Map<String, List<PageEntry>> indexPDF = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        this.pdfsDir = pdfsDir;

        List<PageEntry> pageEntryList = new ArrayList<>();  //нужна ли эта коллекция?

        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы

        StringJoiner sj = new StringJoiner(", "); //

        for (File pdf : pdfsDir.listFiles()) {              //перебор по пдф файлам


            var doc = new PdfDocument(new PdfReader(pdf));
//        doc.getLastPage();
            int amountPage = doc.getPageNumber(doc.getLastPage());


            for (int i = 1; i < amountPage; i++) {
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");

                Map<String, Integer> freqs = new HashMap<>();
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    freqs.put(word.toLowerCase(), freqs.getOrDefault(word, 0) + 1);
                }

                for (Map.Entry<String, Integer> item : freqs.entrySet()) {

                    if(!indexPDF.containsKey(item.getKey())){
                       indexPDF.put(item.getKey(), new ArrayList<>());
                    }
                    indexPDF.get(item.getKey()).add(new PageEntry(pdf.getName(), i, item.getValue()));


                    sj.add("Слово - " + item.getKey() + " = " + pdf.getName() + " стр: " + i + " колич: " + item.getValue());   //
                }

            }
        }

        String ddd = sj.toString();
        //System.out.println(ddd);

//        File resut = new  File ("pdfsDir", "result");
//
//        for (Map.Entry<String, List<PageEntry>> entry : indexPDF.entrySet()) {
//            System.out.println(entry);
//
//        }
//        System.out.println(indexPDF);
    }

    @Override
    public List<PageEntry> search(String word) {
       // List<PageEntry> wordFiltrPageEntry=new ArrayList<>();


//        if (wordFiltrPageEntry.isEmpty()) {
//            return Collections.emptyList();
//        } else{
            return indexPDF.get(word.toLowerCase());
//        }
    }
}
