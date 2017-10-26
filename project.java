import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

public class project2 {
	public static HashMap<Integer, Set<Integer>> docMap = new HashMap<Integer, Set<Integer>>();
	public static HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

	public static void mapgenerator(String s1, String s2) {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(s1);
			br = new BufferedReader(fr);
			String sCurrentLine;
			String sl1, sl2, sl3;
			br = new BufferedReader(new FileReader(s1));
			int docNum = 1;
			Set<Integer> set1;
			while ((sCurrentLine = br.readLine()) != null) {
				sl1 = sCurrentLine.replace("[", "");
				sl2 = sl1.replace("]", "");
				sl3 = sl2.replaceAll(" ", "");
				set1 = new HashSet<Integer>();
				StringTokenizer st1 = new StringTokenizer(sl3, ",");
				while (st1.hasMoreTokens()) {
					String s11 = st1.nextElement().toString();
					// System.out.println(s11);
					set1.add(Integer.parseInt(s11));
				}
				docMap.put(docNum, set1);
				docNum++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		try {
			fr = new FileReader(s2);
			br = new BufferedReader(fr);
			String sCurrentLine1;
			String sl1, sl2, sl3;
			br = new BufferedReader(new FileReader(s2));
			while ((sCurrentLine1 = br.readLine()) != null) {
				StringTokenizer st11 = new StringTokenizer(sCurrentLine1, " = ");
				while (st11.hasMoreTokens()) {
					int num = Integer.parseInt(st11.nextElement().toString());
					String word = st11.nextElement().toString();
					wordMap.put(word, num);
				}
			}
			wordMap.put("=", 24);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static ArrayList<Integer> createPostinList(String s1) {
		String worNum = wordMap.get(s1).toString();
		ArrayList<Integer> retList = new ArrayList<Integer>();
		// System.out.println(worNum);
		for (Object key : docMap.keySet()) {
			// System.out.println("Key : " + key.toString() + " Value : " +
			// docMap.get(key));
			Set<Integer> hmset = docMap.get(key);
			if (hmset.contains(Integer.parseInt(worNum)))
				retList.add(Integer.parseInt(key.toString()));
		}
		Collections.sort(retList);
		return retList;
	}

	public static ArrayList<Integer> createPostinListNot(String s1) {
		String worNum = wordMap.get(s1).toString();
		ArrayList<Integer> retList = new ArrayList<Integer>();
		// System.out.println(worNum);
		for (Object key : docMap.keySet()) {
			// System.out.println("Key : " + key.toString() + " Value : " +
			// docMap.get(key));
			Set<Integer> hmset = docMap.get(key);
			if (!hmset.contains(Integer.parseInt(worNum)))
				retList.add(Integer.parseInt(key.toString()));
		}
		Collections.sort(retList);
		return retList;
	}

	public static ArrayList<Integer> evaluateANDquery(String s1, String s2) {
		ArrayList<Integer> retval = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		retval = createPostinList(s1);
		list2 = createPostinList(s2);
		retval.retainAll(list2);
		Collections.sort(retval);
		return retval;
	}

	public static ArrayList<Integer> evaluateORquery(String s1, String s2) {
		ArrayList<Integer> retval = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		retval = createPostinList(s1);
		list2 = createPostinList(s2);
		retval.addAll(list2);
		Collections.sort(retval);
		return retval;
	}

	public static ArrayList<Integer> evaluateAND_NOTquery(String s1, String s2) {
		ArrayList<Integer> retval = new ArrayList<Integer>();
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		retval = createPostinList(s1);
		list2 = createPostinListNot(s2);
		retval.retainAll(list2);
		Collections.sort(retval);
		return retval;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String s1, s2, type = "", word1 = "", word2 = "";
		File file;
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		ArrayList<Integer> l3 = new ArrayList<Integer>();
		ArrayList<Integer> l4 = new ArrayList<Integer>();
		ArrayList<Integer> l5 = new ArrayList<Integer>();
		s1 = "docs.txt";
		s2 = "vocab_map.txt";
		mapgenerator(s1, s2);
		// l1=createPostinList(Word1);
		// l2=createPostinList(Word2);
		int count = 0;
		Scanner obj = new Scanner(System.in);
		// String array[]=new String[5] ;
		for (int i = 0; i < args.length; i++) {
			count++;

		}
		System.out.println("number of arguments    " + count);
		if (args.length == 3) {
			type = args[0];
			word1 = args[1];
			file = new File(args[2]);
			System.out.println(type);
			System.out.println(word1);
			if ("PLIST".equals(type)) {
				l2 = createPostinList(word1);
				// System.out.println(l1);

			}
			try (FileOutputStream fop = new FileOutputStream(file)) {
				if (!file.exists()) {
					file.createNewFile();
				}
				System.out.println("writing to file");
				PrintWriter pw = new PrintWriter(new FileOutputStream(file));
				pw.write(args[1]);
				pw.write("->");
				pw.write("[");
				for (int i = 0; i < l2.size(); i++) {
					pw.write(l2.get(i).toString());
					pw.write(" ");

				}
				pw.write("]");

				pw.close();

			}
			System.out.println(l2);
		} else if (args.length == 5) {
			file = new File(args[4]);
			type = args[0];
			word1 = args[1];
			word2 = args[3];
			if ("AND".equals(type)) {
				l2 = evaluateANDquery(word1, word2);
			} else if ("OR".equals(type)) {
				l2 = evaluateORquery(word1, word2);
			} else if ("AND_NOT".equals(type)) {
				l2 = evaluateAND_NOTquery(word1, word2);
			}
			try (FileOutputStream fop = new FileOutputStream(file)) {
				if (!file.exists()) {
					file.createNewFile();
				}
				System.out.println("writing to file");
				PrintWriter pw = new PrintWriter(new FileOutputStream(file));
				pw.write(args[1]);
				pw.write(" ");
				pw.write(args[2]);
				pw.write(" ");
				pw.write(args[3]);
				pw.write("->");
				pw.write("[");
				for (int i = 0; i < l2.size(); i++) {
					pw.write(l2.get(i).toString());
					pw.write(" ");

				}
				pw.write("]");
				pw.close();

			}

		}

	}

}
