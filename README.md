# ReutersParser
Java parser for the "Reuters-21578, Distribution 1.0" Text Categorization data set.

Download the dataset and extract the files to any directory.
<br>https://archive.ics.uci.edu/ml/datasets/reuters-21578+text+categorization+collection

You can then iterate over the articles in the data set as follows:

    File file = new File("/tmp/Reuters");
    ReutersParser p = new ReutersParser(file);
    for(ReutersArticle a : p){
        String title = a.getTag("TITLE");
        String body = a.getTag("BODY");
    }
