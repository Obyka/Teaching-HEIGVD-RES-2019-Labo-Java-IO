package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti & Florian Polier
 *
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int lineNumber = 1;
    // ReturnC means the last char was a \r
    private boolean returnC = false;
    private String res = Integer.toString(lineNumber++) + '\t';

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        // On parcourt chaque caractère
        for (int i = off; i < len + off; i++) {
            char d = cbuf[i];
            // Si le caractère précédent était un \r ou que le caractère courant est un \n, il nous faut procéder à un retour
            if (returnC || d == '\n') {
                // Char précédent \r et courant \n -> Windows
                if (returnC && d == '\n') {
                    res += "\r\n";
                }
                // Char précédent \r et pas de \n -> Mac OSX
                else if (returnC) {
                    res += '\r';
                }
                // Char précédent pas \r et courant \n -> Linux
                else {
                    res += '\n';
                }
                // On ajoute le préfixe de la ligne pour tout les cas mentionnés au dessus
                res += Integer.toString(lineNumber++) + '\t';
            }
            // On ajoute tous les caractères non-spéciaux au résultat.
            if (d != '\n' && d != '\r') {
                res += d;
            }
            // On contrôle si le dernier caractère était un \r
            returnC = d == '\r';
        }
        // On écrit dans le writer parent, le résultat final (meilleure performence)
        super.write(res, 0, res.length());
        res = "";
    }

    @Override
    public void write(int c) throws IOException {
        // On crée un tableau contenant un seul char pour pouvoir utiliser la signature de méthode correspondante.
        char[] d = {(char) c};
        write(d, 0, d.length);
    }

}
