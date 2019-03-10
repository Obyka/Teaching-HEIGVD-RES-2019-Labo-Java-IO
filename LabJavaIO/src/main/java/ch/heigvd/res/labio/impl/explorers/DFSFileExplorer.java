package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
      // Si rootDirectory est un fichier, on a fini le parcours
      vistor.visit(rootDirectory);
      if(rootDirectory.isFile()){
          return;
      }
      // On liste les éléments présents dans le répertoire courant et on trie le résultat
      File[] currentFile = rootDirectory.listFiles();
      if(currentFile == null){
        return;
      }
      Arrays.sort(currentFile);

      // Pour chaque élément dans le répertoire, on appelle le visiteur et on continue le parcours dans les sous-dossier
      for(File f : currentFile){
        if(f.isDirectory()){
            explore(f, vistor);
        }
    }
  }

}
