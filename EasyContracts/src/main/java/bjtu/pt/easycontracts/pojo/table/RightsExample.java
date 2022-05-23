package bjtu.pt.easycontracts.pojo.table;

import java.util.ArrayList;
import java.util.List;

public class RightsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RightsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andRightidIsNull() {
            addCriterion("rightId is null");
            return (Criteria) this;
        }

        public Criteria andRightidIsNotNull() {
            addCriterion("rightId is not null");
            return (Criteria) this;
        }

        public Criteria andRightidEqualTo(Integer value) {
            addCriterion("rightId =", value, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidNotEqualTo(Integer value) {
            addCriterion("rightId <>", value, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidGreaterThan(Integer value) {
            addCriterion("rightId >", value, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidGreaterThanOrEqualTo(Integer value) {
            addCriterion("rightId >=", value, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidLessThan(Integer value) {
            addCriterion("rightId <", value, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidLessThanOrEqualTo(Integer value) {
            addCriterion("rightId <=", value, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidIn(List<Integer> values) {
            addCriterion("rightId in", values, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidNotIn(List<Integer> values) {
            addCriterion("rightId not in", values, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidBetween(Integer value1, Integer value2) {
            addCriterion("rightId between", value1, value2, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightidNotBetween(Integer value1, Integer value2) {
            addCriterion("rightId not between", value1, value2, "rightid");
            return (Criteria) this;
        }

        public Criteria andRightnameIsNull() {
            addCriterion("rightName is null");
            return (Criteria) this;
        }

        public Criteria andRightnameIsNotNull() {
            addCriterion("rightName is not null");
            return (Criteria) this;
        }

        public Criteria andRightnameEqualTo(String value) {
            addCriterion("rightName =", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameNotEqualTo(String value) {
            addCriterion("rightName <>", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameGreaterThan(String value) {
            addCriterion("rightName >", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameGreaterThanOrEqualTo(String value) {
            addCriterion("rightName >=", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameLessThan(String value) {
            addCriterion("rightName <", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameLessThanOrEqualTo(String value) {
            addCriterion("rightName <=", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameLike(String value) {
            addCriterion("rightName like", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameNotLike(String value) {
            addCriterion("rightName not like", value, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameIn(List<String> values) {
            addCriterion("rightName in", values, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameNotIn(List<String> values) {
            addCriterion("rightName not in", values, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameBetween(String value1, String value2) {
            addCriterion("rightName between", value1, value2, "rightname");
            return (Criteria) this;
        }

        public Criteria andRightnameNotBetween(String value1, String value2) {
            addCriterion("rightName not between", value1, value2, "rightname");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andFunctionsIsNull() {
            addCriterion("functions is null");
            return (Criteria) this;
        }

        public Criteria andFunctionsIsNotNull() {
            addCriterion("functions is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionsEqualTo(String value) {
            addCriterion("functions =", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsNotEqualTo(String value) {
            addCriterion("functions <>", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsGreaterThan(String value) {
            addCriterion("functions >", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsGreaterThanOrEqualTo(String value) {
            addCriterion("functions >=", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsLessThan(String value) {
            addCriterion("functions <", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsLessThanOrEqualTo(String value) {
            addCriterion("functions <=", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsLike(String value) {
            addCriterion("functions like", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsNotLike(String value) {
            addCriterion("functions not like", value, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsIn(List<String> values) {
            addCriterion("functions in", values, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsNotIn(List<String> values) {
            addCriterion("functions not in", values, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsBetween(String value1, String value2) {
            addCriterion("functions between", value1, value2, "functions");
            return (Criteria) this;
        }

        public Criteria andFunctionsNotBetween(String value1, String value2) {
            addCriterion("functions not between", value1, value2, "functions");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}