package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[ 항목 추가 ]\n"
				+ "제목을 입력하세요 > ");
		
		title = sc.next();
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("같은 제목이 이미 존재합니다.");
			return;
		}
		
		System.out.println("내용을 입력하세요 > ");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[ 항목 삭제 ]\n"
				+ "삭제할 제목을 입력하세요 > ");
		
		String title = sc.next();
		sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[ 항목 수정 ]\n"
				+ "수정할 제목을 입력하세요 > ");
		
		String title = sc.next().trim();
		sc.nextLine();
		if (!l.isDuplicate(title)) {
			System.out.println("제목이 존재하지 않습니다.");
			return;
		}

		System.out.println("새로운 제목을 입력하세요 > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("같은 제목이 이미 존재합니다.");
			return;
		}
		
		System.out.println("새로운 내용을 입력하세요 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println("[" + item.getTitle() + "], " + item.getDesc() + " - "+item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer wr = new FileWriter(filename,true);
			for (TodoItem item : l.getList()) {
				wr.write(item.toSaveString());
			}
			wr.close();
			System.out.println("데이터가 저장되었습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			int count =0;
			while((line = br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line,"##");
				String title = st.nextToken();
				String description = st.nextToken();
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title,description);
				item.setCurrent_date(current_date);
				l.addItem(item);
				count++;
			}
			br.close();
			System.out.println(count+"개의 항목을 불러왔습니다.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
