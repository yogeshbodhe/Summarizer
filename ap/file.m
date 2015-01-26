a=readfis('text');
output = load('d:/proj/summarizer/features.txt');
final = evalfis(output,a);
 fid = fopen('d:/proj/summarizer/ap/features.txt','wt');
 save feat.txt -ascii final    
 fclose(fid);