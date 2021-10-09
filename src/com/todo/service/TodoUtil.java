package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n����Ʈ�� �߰���ų �������� ������ �Է��ϼ���. > ");
		title = sc.next();
		
		if (l.isDuplicate(title)) {
			System.out.println("�̹� �߰��� �����۰� ������ ������ ����� �� �����ϴ�.");
			return;
		}
		
		System.out.print("�������� ī�װ��� �Է��ϼ���. > ");
		category = sc.next();
		
		System.out.print("������ �Է��ϼ���. > ");
		sc.nextLine();
		desc = sc.nextLine();
		
		System.out.print("�������ڸ� �Է��ϼ���. > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(title, desc, null, category, due_date);
		if(l.addItem(t)>0)
			System.out.println("�������� �߰��Ǿ����ϴ�!");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n����Ʈ���� ������ �������� ��ȣ�� �Է��ϼ���. > ");
		int id = sc.nextInt();
		
		for (TodoItem item : l.getList()) {
			if (item.getId()==id) {
				System.out.println(item.toString());
				System.out.print("�� �������� �����Ͻðڽ��ϱ�? (y/n) > ");
				String select = sc.next();
				if(select.contains("y")) {
					if(l.deleteItem(id) > 0)
						System.out.println("�������� �����Ǿ����ϴ�!");
					break;
				}
				else {
					System.out.println("�������� �������� �ʾҽ��ϴ�.");
					break;
				}
			}
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n����Ʈ���� �����ϰ� ���� �������� ��ȣ�� �Է��ϼ���. > ");
		int id = sc.nextInt();
		/*if (l.getList().) {
			System.out.println("��� �Է��Ͻ� ��ȣ�� �������� �ʽ��ϴ�.");
			return;
		}*/
		for (TodoItem item : l.getList()) {
			if (item.getId()==id) 
				System.out.println(item.toString());
		}
		
		System.out.print("���ο� ������ �Է��ϼ���. > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("���� �������� �ٲ� �� �����ϴ�.");
			return;
		}
		
		System.out.print("���ο� ī�װ��� �Է��ϼ���. > ");
		String category = sc.next().trim();
		
		System.out.print("���ο� ������ �Է��ϼ���. > ");
		sc.nextLine();
		String new_description = sc.nextLine().trim();
		
		System.out.print("���ο� �������ڸ� �Է��ϼ���. > ");
		String due_date = sc.next().trim();
		
		
		TodoItem t = new TodoItem(new_title, new_description, category, due_date);
		t.setId(id);
		if(l.updateItem(t) > 0)
			System.out.println("�������� �����Ǿ����ϴ�!");
	}

	public static void listAll(TodoList l) {
		
		System.out.println("[��ü ���, �� " + l.getCount() + "��]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[��ü ���, �� " + l.getCount() + "��]");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.println("\n�� " + count + "���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
	}
	
	public static void find(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void findCate(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("�� " + count + "���� �׸��� ã�ҽ��ϴ�.");
	}
	
	public static void completeItem(TodoList l, int number) {
		if(l.completeItem(number) > 0)
			System.out.println("�ش� �׸� �Ϸ� üũ�Ͽ����ϴ�.");
	}
	
	public static void listAll(TodoList l, int number) {
		int count = 0;
		
		for(TodoItem item : l.getList(number)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.println("\n�� " + count + "���� �׸��� �Ϸ�Ǿ����ϴ�.");
	}
	
	public static void saveList(TodoList l, String filename) {
		int count = 0;
		
		try {
			Writer w = new FileWriter(filename);
			for (TodoItem item : l.getList()) {
				w.write(item.toSaveString());
				count++;
			}
			w.close();
			System.out.println(count + "���� �������� \"todolist.txt\" ���Ͽ� ����Ǿ����ϴ�.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		int count = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String line;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String date = st.nextToken();
				count++;
				
				TodoItem t = new TodoItem(title, description, date, category, due_date);
				l.addItem(t);
			}
			br.close();
			if(count==0) System.out.println("\"todolist.txt\" ���Ͽ� �������� �������� �ʽ��ϴ�.");
			else System.out.println("\"todolist.txt\" ���Ͽ��� " + count + "���� �������� �о�Խ��ϴ�.\n");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("\"todolist.txt\" ������ �����ϴ�.");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}