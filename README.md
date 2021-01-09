# SqlAdapter
Class For Statement to PreparedStatement

if you have like this seriously terrible query.

        String where =        "        WHERE\n" +
                "            article_no = "+parameter[3]+"";
        String sql ="        SELECT\n" +
                "            article_no,\n" +
                "            "+parameter[0]+" AS a,\n" +
                "            "+parameter[1]+" AS b,\n" +
                "            "+parameter[4]+" AS c\n" +
                "        FROM\n" +
                "            freeboard_article\n"+where;
        String where2 = "WHERE\n" +
                "    comment_id = "+parameter[5];
        String sql2 = "SELECT\n" +
                "    comment_id,\n" +
                "    article_no,\n" +
                "    writer_id,\n" +
                "    regdate,\n" +
                "    moddate,\n" +
                "    comment_content,\n" +
                "    a,\n" +
                "    b,\n" +
                "    c\n" +
                +, "+parameter[6] +" AS D"
                "FROM\n" +
                "    ("+sql+")\n" +
                "    JOIN freeboard_article_comment USING ( article_no )\n" +where2 ;
                ;
        Statement preparedStatement = connection.createStatement();
        ResultSet rs= preparedStatement.executeQuery(sql2);
                
and your boss want to change this query.

maybe you will like this.

parameter[0] → ?
pstmt.setString(1, parameter[1]);

it's verry tired work and easy to make mistakes, like me.

then i made this.

you should like this.



        SqlAdapter sqlAdapter = new SqlAdapter();
        String where =        "        WHERE\n" +
                "            article_no = "+sqlAdapter.getPramaterCode(sqlAparameter[3])+"";
        String sql ="        SELECT\n" +
                "            article_no,\n" +
                "            "+sqlAdapter.getParameterCode(parameter[0])+" AS a,\n" +
                "            "+sqlAdapter.getParameterCode(parameter[1])+" AS b,\n" +
                "            "+sqlAdapter.getParameterCode(parameter[4])+" AS c\n" +
                "        FROM\n" +
                "            freeboard_article\n"+where;
        String where2 = "WHERE\n" +
                "    comment_id = "+sqlAdapter.getParameterCode(parameter[5]);
        String sql2 = "SELECT\n" +
                "    comment_id,\n" +
                "    article_no,\n" +
                "    writer_id,\n" +
                "    regdate,\n" +
                "    moddate,\n" +
                "    comment_content,\n" +
                "    a,\n" +
                "    b,\n" +
                "    c\n" +
                +, "+sqlAdapter.getParameterCode(parameter[6]) +" AS D"
                "FROM\n" +
                "    ("+sql+")\n" +
                "    JOIN freeboard_article_comment USING ( article_no )\n" +where2 ;
                ;
        sqlAdapter.setQuery(sql2);
        PreparedStatement preparedStatement = null;
        preparedStatement = sqlAdapter.preparedStatement(connection);
        ResultSet rs= preparedStatement.executeQuery();


I pray that all legacy projects will disappear from this world.
