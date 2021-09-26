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
		
		String title, desc, category,due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[ 항목 추가 ]\n"
				+ "제목 > ");
		
		title = sc.next();
		sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("같은 제목이 이미 존재합니다.");
			return;
		}
		
		System.out.print("카테고리 > ");
		category=sc.next();
		sc.nextLine();
		
		System.out.println("내용 > ");
		desc = sc.nextLine();
		
		System.out.print("마감일자 > ");
		due_date=sc.next();
		sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[ 항목 삭제 ]\n"
				+ "삭제할 번호를 입력하세요 > ");
		
		int num = sc.nextInt();
		sc.nextLine();
		
		int count=1;
		for (TodoItem item : l.getList()) {
			if (count==num) {
				l.deleteItem(item);
				break;
			}
			count+=1;
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "[ 항목 수정 ]\n"
				+ "수정할 번호를 입력하세요 > ");
		
		int num = sc.nextInt();
		sc.nextLine();
		int count=0;
		for (TodoItem item : l.getList()) {
			count+=1;
		}
		if (num>count) {
			System.out.println("번호가 없습니다.");
			return;
		}

		System.out.println("새로운 제목 > ");
		String new_title = sc.next().trim();
		sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("같은 제목이 이미 존재합니다.");
			return;
		}

		System.out.println("새로운 카테고리 > ");
		String new_category = sc.nextLine().trim();
		
		System.out.println("새로운 내용 > ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("새로운 마감일 > ");
		String new_due_date = sc.nextLine().trim();
		
		count=1;
		for (TodoItem item : l.getList()) {
			if (count==num) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description,new_category, new_due_date);
				l.addItem(t);
				System.out.println("수정되었습니다.");
				break;
			}
			count+=1;
		}

	}

	public static void listAll(TodoList l) {
		int count=0;
		for (TodoItem item : l.getList()) {
			count+=1;
		}
		System.out.println("[전체 목록, 총 "+count+"개]");
		count=0;
		for (TodoItem item : l.getList()) {
			count+=1;
			System.out.println(count+". [" + item.getCategory() + "] " + item.getTitle()+" - "+item.getDesc() + " - " + item.getDue_date()+ " - "+item.getCurrent_date());
		}
	}
	
	public static void listKey(TodoList l, String key) {
		int count=0;
		for (TodoItem item : l.getList()) {
			count+=1;
			if(item.getTitle().contains(key)||item.getDesc().contains(key))
			System.out.println(count+". [" + item.getCategory() + "] " + item.getTitle()+" - "+item.getDesc() + " - " + item.getDue_date()+ " - "+item.getCurrent_date());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			Writer wr = new FileWriter(filename);
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
				String category=st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date=st.nextToken();
				String current_date = st.nextToken();
				TodoItem item = new TodoItem(title,description,category,due_date);
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
