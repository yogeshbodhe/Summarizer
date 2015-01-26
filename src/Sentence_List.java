import java.io.BufferedReader;
import java.io.FileReader;


public class Sentence_List {
	public static int[] getSentenceList(int total_sentences,int rate) throws Exception{
		int[] sentenceIndices;
		switch(rate){
			case 0:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.2)];
				break;
			case 1:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.25)];
				break;
			case 2:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.3)];
				break;
			case 3:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.35)];
				break;
			case 4:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.4)];
				break;
			case 5:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.45)];
				break;
			case 6:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.5)];
				break;
			default:	sentenceIndices=new int[(int)Math.ceil(total_sentences*0.35)];
		}
		float[][] indices=new float[total_sentences][2];
		float temp1,temp2;
		String[] defuzzifiedValues=new String[total_sentences];
		
		BufferedReader br=new BufferedReader(new FileReader("files/defuzzified_values.txt"));
		
		for(int i=0;i<total_sentences;i++){
			defuzzifiedValues[i]=br.readLine();
			defuzzifiedValues[i]=defuzzifiedValues[i].trim();
			defuzzifiedValues[i]=defuzzifiedValues[i].substring(0,9);
			indices[i][0]=i;
			indices[i][1]=Float.parseFloat(defuzzifiedValues[i]);
			//System.out.println(indices[i][0]+"\t"+indices[i][1]);
		}
		
		for(int i=0;i<total_sentences-1;i++){
			for(int j=i+1;j<total_sentences;j++){
				if(indices[i][1]<indices[j][1]){
					temp1=indices[i][0];
					temp2=indices[i][1];
					indices[i][0]=indices[j][0];
					indices[i][1]=indices[j][1];
					indices[j][0]=temp1;
					indices[j][1]=temp2;
				}
			}
		}
		
		for(int i=0;i<sentenceIndices.length;i++){
			sentenceIndices[i]=(int)indices[i][0];
			//System.out.println(indices2[i][0]+"\t"+indices2[i][1]);
		}
		
		for(int i=0;i<sentenceIndices.length-1;i++){
			for(int j=i+1;j<sentenceIndices.length;j++){
				if(sentenceIndices[i]>sentenceIndices[j]){
					temp1=sentenceIndices[i];
					sentenceIndices[i]=sentenceIndices[j];
					sentenceIndices[j]=(int)temp1;
				}
			}
		}
		
		/*for(int i=0;i<sentenceIndices.length;i++){
			System.out.println(String.valueOf(sentenceIndices[i]));
		}*/
		
		br.close();
		
		return sentenceIndices;
	}
}