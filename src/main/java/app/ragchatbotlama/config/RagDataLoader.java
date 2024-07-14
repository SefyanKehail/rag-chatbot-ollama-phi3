package app.ragchatbotlama.config;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

@Component
public class RagDataLoader {
    @Value("classpath:/pdfs/cv.pdf")
    private Resource pdfResource;
    @Value("store-data-v1.json")
    private String storeFile;

    @Bean
    public SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel){
        SimpleVectorStore simpleVectorStore = new SimpleVectorStore(embeddingModel);
        String fileStorePath = Path.of("src","main","resources","store").toAbsolutePath()+"/"+storeFile;
        File file = new File(fileStorePath);
        if (!file.exists()){
            PagePdfDocumentReader pdfDocumentReader = new PagePdfDocumentReader(pdfResource);
            List<Document> documentList = pdfDocumentReader.get();
            TextSplitter textSplitter = new TokenTextSplitter();
            List<Document> chunks = textSplitter.split(documentList);
            simpleVectorStore.accept(chunks);
            simpleVectorStore.save(file);
        } else {
            simpleVectorStore.load(file);
        }
        return simpleVectorStore;
    }
}
