package com.example.client;

import org.apache.commons.cli.*;

public class ClientApplication {

	public static void main(String[] args) {
		Options options = new Options();

		Option readers = new Option("r", "readers", true, "Count readers");
		readers.setRequired(true);
		options.addOption(readers);

		Option writers = new Option("w", "writers", true, "Count writers");
		writers.setRequired(true);
		options.addOption(writers);

		Option start = new Option("s", "start", true, "Start key");
		writers.setRequired(true);
		options.addOption(start);

		Option end = new Option("e", "end", true, "End key");
		end.setRequired(true);
		options.addOption(end);

		Option host = new Option("h", "host", true, "Host address");
		host.setRequired(true);
		options.addOption(host);

		Option port = new Option("p", "port", true, "Port");
		port.setRequired(true);
		options.addOption(port);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);

			if (Long.parseLong(cmd.getOptionValue(start.getLongOpt())) > Long.parseLong(cmd.getOptionValue(end.getLongOpt()))) {
				throw new ParseException("Start range greater than end");
			}
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("java -jar client.jar -r <int> -w <int> -s <long> -e <long> -h 127.0.0.1 -p 8080", options);
			System.exit(1);
		}

		int from = Integer.parseInt(cmd.getOptionValue(start.getLongOpt()));
		int to = Integer.parseInt(cmd.getOptionValue(end.getLongOpt()));
		int r = Integer.parseInt(cmd.getOptionValue(readers.getLongOpt()));
		int w = Integer.parseInt(cmd.getOptionValue(writers.getLongOpt()));
		String h = cmd.getOptionValue(host.getLongOpt());
		int p = Integer.parseInt(cmd.getOptionValue(port.getLongOpt()));

		Readers reader = new Readers(r, from, to, h, p);
		Writers writer = new Writers(w, from, to, h, p);

		writer.start();
		// Приостанавливаем поток чтобы Reader не ушел вперед Writer
		// Иначе считает еще не записанные данные
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		reader.start();
	}

}
