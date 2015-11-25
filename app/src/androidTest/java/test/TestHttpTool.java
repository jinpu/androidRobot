package test;

import android.test.AndroidTestCase;
import android.util.Log;
import tools.HttpTools;

/**
 * Created by jinpu on 10/22/15.
 */
public class TestHttpTool extends AndroidTestCase{
    public void testSendINfo()
    {
        String res = HttpTools.doGet("给我讲个笑话");
        Log.e("TAG",res);
        String res1 = HttpTools.doGet("你好");
        Log.e("TAG",res1);
    }
}
