// ConsoleUI.java
package ui;

import dao.UsuarioDAO;
import dao.EntradaGratidaoDAO;
import model.Usuario;
import model.EntradaGratidao;
import service.UsuarioService;
import service.EntradaGratidaoService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private UsuarioService usuarioService;
    private EntradaGratidaoService entradaGratidaoService;
    private final Scanner scanner;

    public ConsoleUI() {
        scanner = new Scanner(System.in);
        try {
            // Carregar o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/BDD_DiarioGratidao?useSSL=false&serverTimezone=UTC",
                    "your_username",
                    "your_password"
            );
            usuarioService = new UsuarioService(new UsuarioDAO(connection));
            entradaGratidaoService = new EntradaGratidaoService(new EntradaGratidaoDAO(connection));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Deletar Usuário");
            System.out.println("4. Adicionar Entrada de Gratidão");
            System.out.println("5. Listar Entradas de Gratidão");
            System.out.println("6. Deletar Entrada de Gratidão");
            System.out.println("0. Sair");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addUsuario();
                    break;
                case 2:
                    listUsuarios();
                    break;
                case 3:
                    deleteUsuario();
                    break;
                case 4:
                    addEntradaGratidao();
                    break;
                case 5:
                    listEntradasGratidao();
                    break;
                case 6:
                    deleteEntradaGratidao();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void addUsuario() {
        System.out.println("Nome:");
        String nome = scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Senha:");
        String senha = scanner.nextLine();
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        try {
            usuarioService.addUsuario(usuario);
            System.out.println("Usuário adicionado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsuarios();
            for (Usuario usuario : usuarios) {
                System.out.println(usuario.getId() + ": " + usuario.getNome() + " - " + usuario.getEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUsuario() {
        System.out.println("ID do usuário a ser deletado:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            usuarioService.removeUsuario(id);
            System.out.println("Usuário deletado com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addEntradaGratidao() {
        System.out.println("Texto de gratidão:");
        String texto = scanner.nextLine();
        System.out.println("Data (YYYY-MM-DD):");
        String dataStr = scanner.nextLine();
        Date data = Date.valueOf(dataStr);
        System.out.println("ID do usuário:");
        int usuarioId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        EntradaGratidao entrada = new EntradaGratidao();
        entrada.setTexto(texto);
        entrada.setData(data);
        entrada.setUsuarioId(usuarioId);

        try {
            entradaGratidaoService.addEntradaGratidao(entrada);
            System.out.println("Entrada de gratidão adicionada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void listEntradasGratidao() {
        System.out.println("ID do usuário (ou 0 para todas as entradas):");
        int usuarioId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            List<EntradaGratidao> entradas;
            if (usuarioId == 0) {
                entradas = entradaGratidaoService.getAllEntradas();
            } else {
                entradas = entradaGratidaoService.getEntradasByUsuarioId(usuarioId);
            }
            for (EntradaGratidao entrada : entradas) {
                System.out.println(entrada.getId() + ": " + entrada.getTexto() + " - " + entrada.getData() + " (Usuário ID: " + entrada.getUsuarioId() + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEntradaGratidao() {
        System.out.println("ID da entrada de gratidão a ser deletada:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        try {
            entradaGratidaoService.removeEntradaGratidao(id);
            System.out.println("Entrada de gratidão deletada com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
