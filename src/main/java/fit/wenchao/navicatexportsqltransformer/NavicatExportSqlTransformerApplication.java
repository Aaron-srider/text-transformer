package fit.wenchao.navicatexportsqltransformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NavicatExportSqlTransformerApplication {

    public static void main(String[] args) {

        SpringApplication.run(NavicatExportSqlTransformerApplication.class, args);

        String testfile = "totp-admin-2023-3-16-10-14-50.sql";

        TextFile textFile = TextFile.from(testfile);
        textFile.convert("DROP TABLE IF EXISTS `[a-zA-Z_]+`;").to("");
        textFile.convert("(CREATE TABLE) `[a-zA-Z_]+`").to("CREATE TABLE IF NOT EXISTS");

    }

}
