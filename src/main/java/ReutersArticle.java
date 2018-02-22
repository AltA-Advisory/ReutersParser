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
public class ReutersArticle {
    private final String rawString;

    public ReutersArticle(String rawString) {
        this.rawString = rawString;
    }

    /**
     * Retrieve tag value.
     * @param tag The xml-ish tag to locate.
     * @return the text delimited by <tag and </tag>. Including any properties that may be in the opening tag.
     */
    public String getTag(String tag){
        String startTag = "<" + tag; //leave out the closing ">".
        String endTag = "</" + tag + ">";
        int startPos = rawString.indexOf(startTag);
        int endPos = rawString.indexOf(endTag);
        if ((startPos == -1) || (endPos == -1)){
            return "";
        } else {
            startPos += startTag.length()+1;
            return rawString.substring(startPos, endPos);
        }
    }
}
