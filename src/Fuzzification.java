import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxyFactory;


public class Fuzzification {
	public static void getDefuzzifiedValues() throws Exception{
		MatlabProxyFactory factory = new MatlabProxyFactory();
		MatlabProxy proxy = factory.getProxy();
		
		proxy.eval("a=readfis('ap/text');");
		proxy.eval("output = load('files/features.txt');");
		proxy.eval("final = evalfis(output,a);");
		//proxy.eval("fid = fopen('D:/proj/summarizer/ap/fea.txt','wt');");
		proxy.eval("save files/defuzzified_values.txt -ascii final;");
		//proxy.eval("fclose(fid);");
	    
		proxy.disconnect();
	}
}
