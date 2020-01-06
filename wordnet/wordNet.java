// import java.io.File;
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;

// public class wordNet {
//     private String name;
//     private File file;
//     private String[] arr1, arr2, arr3;
//     private HashMap<String, List> hashMapObj;
//     public wordNet() {
//     }
//     public wordNet(String fname) {
//         this.name = fname;
//     }
//  private void parseSynsets(String fileName) throws IOException {
   
//     Path path = Paths.get(fileName);
//     List<String> allLines = Files.readAllLines(path);

//     arr1 = new String[allLines.size()];
//     arr2 = new String[allLines.size()];
//     arr3 = new String[allLines.size()];

//     for (int i = 0; i < allLines.size(); i++) {
       
//         arr1[i] = allLines.get(i).split(",")[0];
//         arr2[i] = allLines.get(i).split(",")[1];
//         arr3[i] = allLines.get(i).split(",")[2];

//     }

//     // for (int i = 0; i < arr1.length; i++) {
//     //     System.out.println(arr1[i]);
//     // }

//     // for (int i = 0; i < arr1.length; i++) {
//     //     System.out.println(arr2[i]);
//     // }
    
//     // for (int i = 0; i < arr1.length; i++) {
//     //     System.out.println(arr3[i]);
//     // }
// }
// private void parseHypernyms(String fileName) throws IOException {

//     Path path = Paths.get(fileName);
//     List<String> allLines = Files.readAllLines(path);

//     arr1 = new String[allLines.size()];
//     arr2 = new String[allLines.size()];
//     arr3 = new String[allLines.size()];

//     for (int i = 0; i < allLines.size(); i++) {
//         int arrSize = 0;
//         arrSize = allLines.get(i).split(",").length;
//         if (arrSize == 1) {
//             arr1[i] = allLines.get(i).split(",")[0];
//         } else if (arrSize == 2) {
//             arr1[i] = allLines.get(i).split(",")[0];
//             arr2[i] = allLines.get(i).split(",")[1];
//         } else if (arrSize == 3) {
//             arr1[i] = allLines.get(i).split(",")[0];
//             arr2[i] = allLines.get(i).split(",")[1];
//             arr3[i] = allLines.get(i).split(",")[2];    
//         }
//     }

// }



// public static void main(String[] args) throws IOException {
    
//     File folder = new File("G:\\Github\\ADS-2\\WORDNET");
//     File[] listOfFiles = folder.listFiles();
//     List<wordNet> files = new ArrayList<>();
    
//     for (int i = 0; i < listOfFiles.length; i++) {
//         if (listOfFiles[i].isFile() && listOfFiles[i].getName().startsWith("synsets")) {
//             files.add(new wordNet(listOfFiles[i].getName()));
//         } 
//     }

//     for (int i = 0; i < files.size(); i++) {
//         files.get(i).parseSynsets(files.get(i).getName());
//     }

//     for (int i = 0; i < files.size();i++) {
//         System.out.println(files.get(i).getName());
//     }
// }
    
//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }
// }


// class Synset {
//     private String Id;
//     private String[] synset;
//     private String glossary;
// }