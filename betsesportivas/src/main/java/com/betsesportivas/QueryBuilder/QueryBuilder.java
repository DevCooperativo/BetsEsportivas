package com.betsesportivas.QueryBuilder;

import java.util.List;

public class QueryBuilder {
    private StringBuilder query = new StringBuilder();

    public static QueryBuilder Delete(String table) {
        QueryBuilder builder = new QueryBuilder();
        builder.query.append(String.format("DELETE FROM %s", table));
        return builder;
    }

    public QueryBuilder Select(String[] fields, String table) {
        query.append(" SELECT");

        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                query.append(String.format(" %s.%s", table, fields[i]));
                if (i < fields.length - 1) {
                    query.append(",");
                }
            }
        } else {
            query.append(String.format(" %s.*", table));
        }
        return this;
    }

    public QueryBuilder Sum(String table, String column) {
        query.append(String.format(" SUM(%s.%s)", table, column.isEmpty() ? "*" : column));
        return this;
    }

    public QueryBuilder Count(String table, String column) {
        query.append(String.format(" COUNT(%s.%s)", table, column.isEmpty() ? "*" : column));
        return this;
    }

    public QueryBuilder From(String table) {
        query.append(String.format(" FROM %s", table));
        return this;
    }

    public QueryBuilder LeftJoin(String table, String origin, String option, String target) {
        query.append(String.format(" LEFT JOIN %s ON %s %s %s", table, origin, option, target));
        return this;
    }

    public QueryBuilder InnerJoin(String table, String origin, String option, String target) {
        query.append(String.format(" INNER JOIN %s ON %s %s %s", table, origin, option, target));
        return this;
    }

    public QueryBuilder RightJoin(String table, String origin, String option, String target) {
        query.append(String.format(" RIGHT JOIN %s ON %s %s %s", table, origin, option, target));
        return this;
    }

    public QueryBuilder Where(String origin, boolean state, String option, String target) {
        if (!query.toString().contains("WHERE")) {
            query.append(" WHERE");
        } else {
            query.append(" AND");
        }

        if (state) {
            query.append(String.format(" %s %s %s", origin, option, target));
        } else {
            query.append(String.format(" NOT %s %s %s", origin, option, target));
        }
        return this;
    }

    public QueryBuilder Where(String origin, boolean state, String option, int target) {
        if (!query.toString().contains("WHERE")) {
            query.append(" WHERE");
        } else {
            query.append(" AND");
        }

        if (state) {
            query.append(String.format(" %s %s %d", origin, option, target));
        } else {
            query.append(String.format(" NOT %s %s %d", origin, option, target));
        }
        return this;
    }

    public QueryBuilder Where(String condition) {
        if (!query.toString().contains("WHERE")) {
            query.append(" WHERE ").append(condition);
        } else {
            query.append(" AND ").append(condition);
        }
        return this;
    }

    public QueryBuilder WhereIn(String column, String table, QueryBuilder subquery, boolean negated) {
        if (!query.toString().contains("WHERE")) {
            query.append(" WHERE ");
        } else {
            query.append(" AND ");
        }

        if (negated) {
            query.append(String.format("%s.%s NOT IN (%s)", table, column, subquery.toString()));
        } else {
            query.append(String.format("%s.%s IN (%s)", table, column, subquery.toString()));
        }
        return this;
    }

    public <T> QueryBuilder WhereIn(String column, String table, List<T> values, boolean negated) {
        query.append(String.format(" WHERE %s.%s", table,column));
        if(negated){
            query.append(" NOT");
        }
        query.append(" IN (");
        for(int i = 0; i < values.size(); i++){
            String convertedValue = values.get(i).toString();
            if(i<values.size()-1){
                query.append(String.format(" %s,", convertedValue).toString());
            }
            else{
                query.append(String.format(" %s", convertedValue).toString());
            }

        }
        query.append(" )");
        return this;
    }

    public QueryBuilder GroupBy(String column, String table) {
        if (!query.toString().contains("GROUP BY")) {
            query.append(String.format(" GROUP BY %s.%s", table, column));
        } else {
            query.append(String.format(", %s.%s", table, column));
        }
        return this;
    }

    public QueryBuilder OrderBy(String column, String table) {
        if (!query.toString().contains("ORDER BY")) {
            query.append(String.format(" ORDER BY %s.%s", table, column));
        } else {
            query.append(String.format(", %s.%s", table, column));
        }
        return this;
    }

    @Override
    public String toString() {
        return query.toString();
    }
}
