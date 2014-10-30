package edu.mioib.qaplocalsearch.saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;

@Data
public class GenericExperimentSaver {
	public static void save(String path, List<String> columnsNames, List<List<String>> valueRows) {
		try {
			File file = new File(path);

			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write(StringUtils.join(columnsNames, ";"));
			writer.newLine();
			for (List<String> row : valueRows) {
				writer.write(StringUtils.join(row, ";"));
				writer.newLine();
			}
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
