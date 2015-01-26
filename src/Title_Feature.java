
public class Title_Feature {
	public static float[][] calculateTitleFeature(String[][][] tokenizer_output,int paragraph_count,String[][] segment_output,int title_length){
		float titleFeature[][]=new float[50][50];
		int title_word_counter=0;
		
		for(int i=0;i<paragraph_count;i++){
			for(int j=0;j<segment_output[i].length;j++){
				title_word_counter=0;
				outer:for(int k=0;k<tokenizer_output[i][j].length;k++){
					inner:for(int l=0;l<tokenizer_output[0][0].length;l++){
						if(tokenizer_output[i][j][k].equals(tokenizer_output[0][0][l])){
							if((tokenizer_output[i][j][k].equals("."))||
									(tokenizer_output[i][j][k].equals("?")||
									(tokenizer_output[i][j][k].equals("!"))||
									(tokenizer_output[i][j][k].equals(","))||
									(tokenizer_output[i][j][k].equals(":"))||
									(tokenizer_output[i][j][k].equals(";"))||
									(tokenizer_output[i][j][k].equals("("))||
									(tokenizer_output[i][j][k].equals(")"))||
									(tokenizer_output[i][j][k].equals("'"))||
									(tokenizer_output[i][j][k].equals("'s")))){
								continue inner;
							}
							else{
								title_word_counter++;
								continue outer;
							}
						}
					}
				}
				titleFeature[i][j]=((float)title_word_counter/(float)title_length);
			}
		}
		
		return titleFeature;
	}
}
