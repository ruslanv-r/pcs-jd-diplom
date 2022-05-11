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

        // прочтите тут все pdf и сохраните нужные данные,
        // тк во время поиска сервер не должен уже читать файлы

        var doc = new PdfDocument(new PdfReader(pdf));
//        doc.getLastPage();
        int amountPage = doc.getPageNumber(doc.getLastPage());

        for (int i = 0; i < amountPage - 1; i++) {
            var page = doc.getPage(i);
            var text = PdfTextExtractor.getTextFromPage(page);
            var words = text.split("\\P{IsAlphabetic}+");

            Map<String, Integer> freqs = new HashMap<>();
            for (String word : words) {
                if (word.isEmpty()) {
                    continue;
                }
                freqs.put(word.toLowerCase(),freqs.getOrDefault(word, 0)+1);
            }


        }


    }

    @Override
    public List<PageEntry> search(String word) {
        // тут реализуйте поиск по слову
        return Collections.emptyList();
    }
}
