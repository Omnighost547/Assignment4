// Fig. 8.31: DisplayQueryResults.java
// Display the contents of the Authors table in the
// Books database.

// Java core packages

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

// Java extension packages
import javax.swing.*;
import javax.swing.table.*;

public class DisplayQueryResults extends JFrame {
    private ResultSetTableModel tableModel;

    // create ResultSetTableModel and GUI
    public DisplayQueryResults() {
        super("Displaying Query Results");

        // Cloudscape database driver class name
        String driver = "com.mysql.cj.jdbc.Driver";

        // Parameters to connect to books database
        String url = "jdbc:mysql://localhost:3306/books";
        String user = "root";
        String password = "TestPassw0rd";

        // queries to select entire tables
        String queryAuthors = "SELECT * FROM authors";
        String createQuery = "";

        // create ResultSetTableModel and display database table
        try {
            // create TableModel for results of query
            // SELECT * FROM authors
            tableModel = new ResultSetTableModel(driver, url, user, password, queryAuthors);

            // set up JTextArea in which user types queries
            JTextArea queryArea = new JTextArea(queryAuthors, 3, 35);
            queryArea.setWrapStyleWord(true);
            queryArea.setLineWrap(true);

            JScrollPane scrollPane = new JScrollPane(queryArea,
                    ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            // set up JButton for submitting queries
            JButton submitButton = new JButton("Submit Query");
            JButton showAuthorsButton = new JButton("Show Authors");
            JButton showPublishersButton = new JButton("Show Publishers");
            JButton showTitlesButton = new JButton("Show Titles");
            JButton showISBNButton = new JButton("Show Author ISBNs");

            Box showBox = Box.createHorizontalBox();
            showBox.add(showAuthorsButton);
            showBox.add(showPublishersButton);
            showBox.add(showTitlesButton);
            showBox.add(showISBNButton);

            // create Box to manage placement of queryArea and
            // submitButton in GUI
            Box queryBox = Box.createHorizontalBox();
            queryBox.add(scrollPane);
            queryBox.add(submitButton);
            queryBox.setMinimumSize(queryBox.getPreferredSize());

            // create JTable delegate for tableModel and remove blank space underneath on first draw
            JTable resultTable = new JTable(tableModel) {
                @Override
                public Dimension getPreferredScrollableViewportSize() {
                    return new Dimension(super.getPreferredSize().width, getRowHeight() * getRowCount());
                }
            };

            JButton addAuthorButton = new JButton("Add Author");
            JButton addPublisherButton = new JButton("Add Publisher");
            JButton addTitleButton = new JButton("Add Title");

            Box createBox = Box.createHorizontalBox();
            createBox.add(addAuthorButton);
            createBox.add(addPublisherButton);
            createBox.add(addTitleButton);

            // place GUI components on content pane
            Container c = getContentPane();
            c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
            c.add(showBox, BorderLayout.BEFORE_FIRST_LINE);
            c.add(queryBox, BorderLayout.NORTH);
            c.add(new JScrollPane(resultTable), BorderLayout.CENTER);
            c.add(createBox, BorderLayout.SOUTH);

            // create event listeners for buttons
            submitButton.addActionListener( new assignQuery(queryArea.getText()) ); // end call to addActionListener

            showAuthorsButton.addActionListener( new assignQuery(queryAuthors) );

            showPublishersButton.addActionListener( new assignQuery("SELECT * FROM publishers") );

            showTitlesButton.addActionListener( new assignQuery("SELECT * FROM titles") );

            showISBNButton.addActionListener( new assignQuery("SELECT * FROM authorisbn") );

            addAuthorButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                new AuthorEntry(driver, url, user, password);
                        }
                    }
            );

            setMinimumSize(c.getPreferredSize());
        }  // end try

        // catch ClassNotFoundException thrown by
        // ResultSetTableModel if database driver not found
        catch (ClassNotFoundException classNotFound) {
            JOptionPane.showMessageDialog(null,
                    "Cloudscape driver not found", "Driver not found",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);   // terminate application
        }

        // catch SQLException thrown by ResultSetTableModel
        // if problems occur while setting up database
        // connection and querying database
        catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null,
                    sqlException.toString(),
                    "Database error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);   // terminate application
        }
    }  // end DisplayQueryResults constructor

    // The query buttons had a lot of similar code, so I made an inner class.
    private class assignQuery implements ActionListener {
        private String query;

        private assignQuery(String query) {
            this.query = query;
        }

        // pass query to table model
        @Override
        public void actionPerformed(ActionEvent e) {
            // perform a new query
            try {
                tableModel.setQuery(query);
            }
            // catch SQLExceptions that occur when performing a new query
            catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.toString(), "Database error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }  // end actionPerformed
    }

//    // execute application
//    public static void main(String[] args) {
//        DisplayQueryResults app = new DisplayQueryResults();
//        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
}  // end class DisplayQueryResults


/* ************************************************************************
 * (C) Copyright 2001 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
