package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the argument does not
     * contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
        String[] res = new String[2];
        for (int i = 0; i < lines.length(); i++) {
            // Si le char est un \n, on prend tout jusqu'à lui (marche pour \n et \r\n)
            if (lines.charAt(i) == '\n') {
                res[0] = lines.substring(0, i + 1);
                res[1] = lines.substring(i + 1);
                return res;
            }
            // Si le char précédent est un \r mais qu'on a pas de \n on, on prend jusqu'au \r
            else if (i > 0 && lines.charAt(i) != '\n' && lines.charAt(i - 1) == '\r') {
                res[0] = lines.substring(0, i);
                res[1] = lines.substring(i);
                return res;
            }
            // Si le \r est le dernier char, on doit le traiter tout de suite
            else if(i == lines.length()-1 && lines.charAt(i) == '\r'){
                res[0] = lines;
                res[1] = "";
                return res;
            }
        }
        res[0] = "";
        res[1] = lines;
        return res;
    }
}