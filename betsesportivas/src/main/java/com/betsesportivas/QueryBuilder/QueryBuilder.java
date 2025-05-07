package com.betsesportivas.QueryBuilder;

import java.time.LocalDateTime;
import java.util.List;

import com.betsesportivas.Helpers.ParserHelper;

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

    public QueryBuilder Update(String table) {
        query.append(String.format("UPDATE %s", table));
        return this;
    }

    public QueryBuilder Set(String column, String value) {
        if (!query.toString().contains("SET")) {
            query.append(String.format(" SET %s = '%s'", column, value));
        } else {
            query.append(String.format(", %s = '%s'", column, value));
        }
        return this;
    }

    public QueryBuilder Set(String column, int value) {
        if (!query.toString().contains("SET")) {
            query.append(String.format(" SET %s = %d", column, value));
        } else {
            query.append(String.format(", %s = %d", column, value));
        }
        return this;
    }

    public QueryBuilder Set(String column, double value) {
        if (!query.toString().contains("SET")) {
            query.append(String.format(" SET %s = %s", column, ParserHelper.doubleToString(value)));
        } else {
            query.append(String.format(", %s = %s", column, ParserHelper.doubleToString(value)));
        }
        return this;
    }

    public QueryBuilder Insert(String table, List<String> columns) {
        query.append(String.format(" INSERT INTO %s (", table));
        for (int i = 0; i < columns.size(); i++) {
            String convertedValue = columns.get(i);
            if (i < columns.size() - 1) {
                query.append(String.format(" %s,", convertedValue));
            } else {
                query.append(String.format(" %s", convertedValue));
            }
        }
        query.append(" )");
        return this;
    }

    public QueryBuilder InsertValue(String value) {
        if (!query.toString().contains(("VALUES"))) {
            query.append(String.format(" VALUES( '%s'", value));
        } else {
            query.append(String.format(", '%s'", value));
        }
        return this;
    }

    public QueryBuilder InsertValue(double value) {
        if (!query.toString().contains(("VALUES"))) {
            query.append(String.format(" VALUES( %s", ParserHelper.doubleToString(value)));
        } else {
            query.append(String.format(", %s", ParserHelper.doubleToString(value)));
        }
        return this;
    }

    public QueryBuilder InsertValue(int value) {
        if (!query.toString().contains(("VALUES"))) {
            query.append(String.format(" VALUES( %d", value));
        } else {
            query.append(String.format(", %d", value));
        }
        return this;
    }

    public QueryBuilder InsertValue(LocalDateTime value) {
        if (!query.toString().contains(("VALUES"))) {
            query.append(String.format(" VALUES( '%s'", value.toString()));
        } else {
            query.append(String.format(", '%s'", value.toString()));
        }
        return this;
    }

    public QueryBuilder EndInsertValue() {
        query.append(" )");
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
        query.append(String.format(" WHERE %s.%s", table, column));
        if (!negated) {
            query.append(" NOT");
        }
        query.append(" IN (");
        for (int i = 0; i < values.size(); i++) {
            String convertedValue = values.get(i).toString();
            if (i < values.size() - 1) {
                query.append(String.format(" %s,", convertedValue).toString());
            } else {
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

    public QueryBuilder Returning(String table, List<String> column) {
        query.append(" RETURNING");
        for (int i = 0; i < column.size(); i++) {
            if (i < column.size() - 1) {
                query.append(String.format(" %s.%s,", table, column.get(i)));
            } else {
                query.append(String.format(" %s.%s", table, column.get(i)));
            }
        }
        return this;
    }

    public QueryBuilder emptyQuery() {
        query = new StringBuilder();
        return this;
    }

    @Override
    public String toString() {
        return query.toString();
    }
}
