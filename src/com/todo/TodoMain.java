 package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static String filename="todolist.txt";
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		
		TodoUtil.loadList(l, filename);
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {
			
			case "help":
				Menu.displaymenu();
				break;

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "find":
				String key=sc.next();
				TodoUtil.listKey(l,key);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;

			case "exit":
				TodoUtil.saveList(l,filename);
				quit = true;
				break;

			default:
				System.out.println("정확한 명령어를 입력하세요. ( 도움말 - help )");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
	}
}
