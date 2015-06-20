import java.io.File;

public class DirTeste {

	private static void dirlist(String fname) {
		File dir = new File(fname);
		String[] chld = dir.list();
		if (chld == null) {
			System.out
					.println("Specified directory does not exist or is not a directory.");
			System.exit(0);
		} else {
			for (int i = 0; i < chld.length; i++) {
				if (chld[i].endsWith(".xml")) {
					String fileName = chld[i];
					System.out.println(fileName);
				}
			}
		}
	}

	public static void main(String[] args) {
		dirlist("c:\\xml");

	}

}
