a = readfis('sentence');
a = addvar(a,'input','noun',[0 30]);
a = addmf(a,'input',1,'Low','gaussmf',[0 10 10]);
a = addmf(a,'input',1,'Medium','gaussmf',[10 15 20]);
a = addmf(a,'input',1,'High','gaussmf',[20 25 30]);
plotmf(a,'input',1)