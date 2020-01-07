import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WordNet1 {
    List<Synset> synsetObjList;
    List<Integer> hypMapObj;
    private HashMap<String, Synset> hashMapObj;
    private HashMap<Integer, List<String>> hypernymsHashMapObj;
    public WordNet1() {
        synsetObjList = new ArrayList<>();
        hashMapObj = new HashMap<>();
        hypernymsHashMapObj = new HashMap<>();
        hypMapObj = new ArrayList<>();
    }
    class Synset { 
        private String id;
        private String[] synsetArr;
        private String glossary;

        public Synset() {

        }

        public Synset(String synsetId, String[] ssArr, String glsy) {
            this.id = synsetId;
            this.synsetArr = ssArr;
            this.glossary = glsy;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String[] getSynsetArr() {
            return synsetArr;
        }

        public void setSynsetArr(String[] synsetArr) {
            this.synsetArr = synsetArr;
        }

        public String getGlossary() {
            return glossary;
        }

        public void setGlossary(String glossary) {
            this.glossary = glossary;
        }

        @Override
        public String toString() {
            return "Synset [" + " id = " + id + " synsetArr = " + Arrays.toString(synsetArr) + " glossary = " + glossary + " ]";
        }
    } 

        private void parseSynsets(String fileName) throws IOException {
            Path path = Paths.get(fileName);
            List<String> allLines = Files.readAllLines(path);
            for (int i = 0; i < allLines.size(); i++) {
                String[] arr = allLines.get(i).split(",");
                Synset obj = new Synset(arr[0], arr[1].split(" "), arr[2]);
                hashMapObj.put(arr[0], obj);
                synsetObjList.add(obj);
            }
        }

        private void parseHypernyms(String fileName) throws IOException {
            Path path = Paths.get(fileName);
            List<String> allLines = Files.readAllLines(path);
            String[] arr;
            for (int i = 0; i < allLines.size(); i++) {
                if (allLines.get(i).contains(",")) {
                    arr = allLines.get(i).split(",",2);
                    hypernymsHashMapObj.put(Integer.parseInt(arr[0]), Arrays.asList(arr[1]));
                    hypMapObj.add(Integer.parseInt(arr[0]));
                    for (String key : arr[1].split(",")) {
                        hypernymsHashMapObj.put(Integer.parseInt(key), null);
                    }
                } else {
                    arr = allLines.get(i).split(" ");
                    hypernymsHashMapObj.put(Integer.parseInt(arr[0]), null);
                    hypMapObj.add(Integer.parseInt(arr[0]));
                }
            }
        }

    public static void main(String[] args) throws IOException {
    WordNet1 wordNet1Obj  = new WordNet1();
    File folder = new File("G:\\Github\\ADS-2\\WORDNET");
    File[] listOfFiles = folder.listFiles();
    
    for (int i = 0; i < listOfFiles.length; i++) {
        if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("synsets")) {
            wordNet1Obj.parseSynsets(listOfFiles[i].getName());
        } else if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("hypernyms")) {
            wordNet1Obj.parseHypernyms(listOfFiles[i].getName());
        }
    }

    System.out.println("SIZE " + wordNet1Obj.hashMapObj.size());
    
    for (int i = 0; i < wordNet1Obj.hashMapObj.size(); i++) {
        System.out.println(wordNet1Obj.hashMapObj.get(wordNet1Obj.synsetObjList.get(i).id));
    }

    System.out.println("SIZE " + wordNet1Obj.hypMapObj.size());
    
    for (int i = 0; i < wordNet1Obj.hypMapObj.size(); i++) {
        System.out.println(wordNet1Obj.hypMapObj.get(i));
        System.out.println(wordNet1Obj.hypernymsHashMapObj.get(wordNet1Obj.hypMapObj.get(i)));
    }
    
    }
}