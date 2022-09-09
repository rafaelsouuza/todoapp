package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.Task;
import util.ConnectionFactory;

public class ProjectController {

    public void save(Project project) {

        String sql = "INSERT INTO projects (name,"
                + "description,"
                + "createdAt,"
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.execute();

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto " + ex.getMessage(), ex);

        } finally {
            ConnectionFactory.closeConnetion(conn, statement);
        }

    }

    public void update(Project project) {

        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {
            
            // Estabelecendoa a conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            // Preparando a query
            statement = conn.prepareStatement(sql);
            
            // Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.setInt(5, project.getId());
            
            // Executando a query
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar o projeto " + ex.getMessage(), ex);

        } finally {
            ConnectionFactory.closeConnetion(conn, statement);
        }

    }

    public void removeById(int projectId) {

        String sql = "DELETE FROM projects WHERE id = ?";

        Connection conn = null;
        PreparedStatement statement = null;

        try {

            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, projectId);
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar o projeto " + ex.getMessage(), ex);

        } finally {
            ConnectionFactory.closeConnetion(conn, statement);
        }

    }

    public List<Project> getAll() {

        String sql = "SELECT * FROM projects";

        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        // lista de tarefas que será devolvida quando a chamada do método acontecer
        List<Project> projects = new ArrayList<>();

        try {

            conn = ConnectionFactory.getConnection();
            statement = conn.prepareStatement(sql);
            
            // Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdateAt(resultSet.getDate("updatedAt"));

                projects.add(project);

            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao listar os projeto " + ex.getMessage(), ex);

        } finally {
            ConnectionFactory.closeConnetion(conn, statement, resultSet);
        }

        return projects;
    }

}
