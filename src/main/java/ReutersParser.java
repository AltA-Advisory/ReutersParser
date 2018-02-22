/*-
 *
 *  * Copyright 2018 Alt&A Advisory, Inc.
 *  *
 *  *    Licensed under the Apache License, Version 2.0 (the "License");
 *  *    you may not use this file except in compliance with the License.
 *  *    You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *    Unless required by applicable law or agreed to in writing, software
 *  *    distributed under the License is distributed on an "AS IS" BASIS,
 *  *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *    See the License for the specific language governing permissions and
 *  *    limitations under the License.
 *
 */

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Parser for the "Reuters-21578, Distribution 1.0" Text Categorization data set.
 * see:
 * https://archive.ics.uci.edu/ml/datasets/reuters-21578+text+categorization+collection
 *
 */
public class ReutersParser implements Iterable<ReutersArticle>{

    private final LinkedList<File> fileList;

    public ReutersParser(File directory ) {
        File[] files = directory.listFiles(
                file -> file.getName().endsWith(".sgm")
        );
        if (files == null){
            throw new RuntimeException("ReutersParser: Empty file list. Point the parser to the directory with the sgm data files");
        }

        fileList = new LinkedList<>();
        fileList.addAll(Arrays.asList(files));
    }

    class ArticleIterator implements Iterator<ReutersArticle> {

        private final Iterator<File> fileIterator;
        private BufferedReader file_reader;
        private ReutersArticle nextArticle;

        /**
         * In order to answer hasNext correctly we need to read one article ahead of the iterator.
         * @throws IOException on any IO issue.
         */
        private void prepareNext() throws IOException {
            StringBuilder buffer = new StringBuilder();
            while(true) {
                String line = file_reader.readLine();
                if (line == null) { //We have reached the end of the current file.
                    file_reader.close();

                    if (fileIterator.hasNext()){
                        file_reader = new BufferedReader(new FileReader(fileIterator.next()));
                        continue; //try again from the new file.
                    } else {
                        // There is no next file!
                        nextArticle = null;
                        return;
                    }
                }

                buffer.append(line);
                if (!line.contains("</REUTERS>")) {
                    continue;
                }

                nextArticle = new ReutersArticle(buffer.toString());
                break;

            }
        }


        public ArticleIterator(Iterator<File> fileIterator){
            this.fileIterator = fileIterator;
            try {
                file_reader = new BufferedReader(new FileReader(fileIterator.next()));
                prepareNext();
            } catch (IOException e) {
                file_reader = null;
                nextArticle = null;
            }
        }

        @Override
        public boolean hasNext() {
            return nextArticle != null;
        }

        @Override
        public ReutersArticle next() {
            ReutersArticle res = this.nextArticle;
            try {
                prepareNext();
            } catch (IOException e) {
                throw new RuntimeException("IOException in .next of Reutersarticle iterator. " + Arrays.toString(e.getStackTrace()));
            }
            return res;
        }
    }

    @Override
    public Iterator<ReutersArticle> iterator() {
        return new ArticleIterator(fileList.iterator());
    }
}
