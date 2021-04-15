package com.gisaklc.cursomc.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Teste implements Iterable<String> {
	private static Set<String> telefones = new HashSet<String>();

	private static void adicionar() {
		telefones.add("Bruno");
		telefones.add("Adrien");

		Set<String> ordena = new TreeSet<String>(telefones);

		Iterator<String> it = telefones.iterator();

		while (it.hasNext()) {
			String telefone = it.next();
			System.out.println(telefone);
		}

	}

	public static void main(String[] args) {

		adicionar();

	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return telefones.iterator();
	}

}
