import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Coordinator {

	/**
	 * @param args
	 */
	public static String[] coordinate(File file,int rate,int category) throws Exception{
		// TODO Auto-generated method stub
		
		String paragraph[]=new String[50];
		String segment_output[][]=new String[50][50];
		String tokenizer_output[][][]=new String[50][50][100];
		String postagger_output[][][]=new String[50][50][100];
		
		int paragraph_count=0,sentence_length[][]=new int[50][50],
				sentence_length_counter=0,total_sentences=0,
				sentenceIndices[];
		
		float sentenceLengthFeature[][]=new float[50][50];
		float sentencePositionFeature[][]=new float[50][50];
		float titleFeature[][]=new float[50][50];
		float properNounFeature[][]=new float[50][50];
		float numericalDataFeature[][]=new float[50][50];
		
		String[] sentences;
		
		PrintWriter summaryFile=new PrintWriter(new FileWriter("files/summary.txt"));
		PrintWriter segmentationFile=new PrintWriter(new FileWriter("files/segmentation.txt"));
		PrintWriter tokenizationFile=new PrintWriter(new FileWriter("files/tokenization.txt"));
		PrintWriter postaggingFile=new PrintWriter(new FileWriter("files/pos-tagging.txt"));
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		//System.out.println("Enter pathname of file:");
		//String pathname=br.readLine();
		//File file=new File(pathname);
		BufferedReader reader=new BufferedReader(new FileReader(file));
		/*if(reader==null){
			System.out.println("File not found");
			return null;
		}*/
		
		while((paragraph[paragraph_count++]=reader.readLine())!=null){
			if(reader.readLine()==null){
				break;
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			segment_output[i]=Segmentation.segment(paragraph[i]);
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				segmentationFile.println((segment_output[i][j]));
			}
		}
		
		segmentationFile.close();
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				tokenizer_output[i][j]=Tokenizer.tokenize(segment_output[i][j]);
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					tokenizationFile.print((tokenizer_output[i][j][k]+"\t"));
				}
				tokenizationFile.println();
			}
			tokenizationFile.println();
		}
		
		tokenizationFile.close();
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				postagger_output[i][j]=POSTagging.postagger(tokenizer_output[i][j]);
			}
		}
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					postaggingFile.print((postagger_output[i][j][k]+"\t"));
				}
				postaggingFile.println();
			}
			postaggingFile.println();
		}
		
		postaggingFile.close();
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				total_sentences++;
				sentence_length_counter=0;
				for(int k=0;k<tokenizer_output[i][j].length;k++){
					if((postagger_output[i][j][k].equals("."))||
						(postagger_output[i][j][k].equals("?")||
						(postagger_output[i][j][k].equals("!"))||
						(postagger_output[i][j][k].equals(","))||
						(postagger_output[i][j][k].equals(":"))||
						(postagger_output[i][j][k].equals(";"))||
						(postagger_output[i][j][k].equals("-LRB-"))||
						(postagger_output[i][j][k].equals("-RRB-"))||
						(postagger_output[i][j][k].equals("POS"))||
						(postagger_output[i][j][k].equals("''")))){
						continue;
					}
					else{
						sentence_length[i][j]=++sentence_length_counter;
					}
				}
			}
		}
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(sentence_length[i][j]));
			}
		}*/
		
		sentenceLengthFeature=Sentence_Length_Feature.calculateSentenceLengthFeature(paragraph_count,segment_output,sentence_length);
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(sentenceLengthFeature[i][j]));
			}
		}*/
		
		sentencePositionFeature=Sentence_Position_Feature.calculateSentencePositionFeature(paragraph.length,segment_output);
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(sentencePositionFeature[i][j]));
			}
		}*/
		
		titleFeature=Title_Feature.calculateTitleFeature(tokenizer_output,paragraph_count,segment_output,sentence_length[0][0]);
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(titleFeature[i][j]));
			}
		}*/
		
		properNounFeature=Proper_Noun_Feature.calculateProperNounFeature(paragraph_count, segment_output, postagger_output, sentence_length);
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(properNounFeature[i][j]));
			}
		}*/
		
		numericalDataFeature=Numerical_Data_Feature.calculateNumericalDataFeature(paragraph_count, segment_output, postagger_output, sentence_length);
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(numericalDataFeature[i][j]));
			}
		}*/
		
		Term_Weight_Feature.calculateTermWeightFeature(paragraph_count,segment_output,tokenizer_output,total_sentences,titleFeature,sentenceLengthFeature,sentencePositionFeature,properNounFeature,numericalDataFeature,category);
		
		/*for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				System.out.println(String.valueOf(termWeightFeature[i][j]));
			}
		}*/
		
		Fuzzification.getDefuzzifiedValues();
		
		int x=0;
		sentences=new String[total_sentences];
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				sentences[x++]=segment_output[i][j];
			}
		}
		
		sentenceIndices=Sentence_List.getSentenceList(total_sentences,rate);
		
		String[] summarySentences;
		int t=0;
		
		if(sentenceIndices[0]!=0){
			summaryFile.println(sentences[0]);
			summarySentences=new String[sentenceIndices.length+1];
			summarySentences[t++]=sentences[0];
		}
		else{
			summarySentences=new String[sentenceIndices.length];
		}
		
		for(int i=0;i<sentenceIndices.length;i++){
			summaryFile.println(sentences[sentenceIndices[i]]);
			summarySentences[t++]=sentences[sentenceIndices[i]];
		}
		
		summaryFile.close();
		br.close();
		reader.close();
		
		return summarySentences;
	}
}