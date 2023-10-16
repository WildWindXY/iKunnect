
//import org.apache.logging.log4j.Logger;

import client.entity.TranslationRequest;
import client.entity.TranslationResponse;
import client.interface_adapter.DeeplAPI;
import client.interface_adapter.TranslationAPI;
import client.use_case.DeeplTranslationService;
import client.use_case.TranslationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IKunnect {
    public static void main(String[] args) {
        // print default text encode mode
        System.out.println(Charset.defaultCharset());
        TranslationAPI deeplAPI = new DeeplAPI();
        TranslationService service = new DeeplTranslationService(deeplAPI);

        TranslationRequest request = new TranslationRequest("ZH", "EN", "关于全角和半角：\n" +
                "\n" +
                "中文字符，确切地说，CJK字符（中文、日文、韩文）主要是全角字符，而拉丁字母、数字和某些符号等主要是半角。但是，我们也有全角的拉丁字母、数字和符号，例如全角的空格、逗号、句号等。全角字符在显示时通常比半角字符宽，通常是它的两倍。\n" +
                "\n" +
                "关于输入法和编码问题：\n" +
                "\n" +
                "输入法之间可能存在差异，有时候，它们可能会生成并非标准UTF-8编码的字符，尤其是一些旧的或特定于平台的输入法。此外，某些字符可能存在于Unicode中，但不是UTF-8的一部分，或者它们可能不是所用系统的默认字符集的一部分。\n" +
                "\n" +
                "解决方法：\n" +
                "\n" +
                "确保你的系统和程序都支持UTF-8：这样，无论你的输入法产生什么样的字符，只要它们是有效的Unicode字符，它们就可以被正确地编码和解码。\n" +
                "更新输入法：如果你的输入法是导致问题的原因，尝试更新到最新版本，或更换到一个更加标准和流行的输入法。\n" +
                "字符检查和转换：可以使用代码来检查字符串中的每个字符，确保它们都是有效的UTF-8字符。对于那些不是的字符，你可以尝试转换它们，或者用其他字符替换它们。\n" +
                "使用Java中的Charset：例如，如果你知道输入是特定的字符集，例如GB2312，但你想将其转换为UTF-8，你可以使用Java中的Charset和ByteBuffer类来转换编码。");
        TranslationResponse response = service.requestTranslate(request);


        System.out.println("Translated Text: " + response.getTranslatedText());
    }
}
