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

        public Criteria andUseUseridIsNull() {
            addCriterion("Use_userId is null");
            return (Criteria) this;
        }

        public Criteria andUseUseridIsNotNull() {
            addCriterion("Use_userId is not null");
            return (Criteria) this;
        }

        public Criteria andUseUseridEqualTo(Integer value) {
            addCriterion("Use_userId =", value, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridNotEqualTo(Integer value) {
            addCriterion("Use_userId <>", value, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridGreaterThan(Integer value) {
            addCriterion("Use_userId >", value, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("Use_userId >=", value, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridLessThan(Integer value) {
            addCriterion("Use_userId <", value, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridLessThanOrEqualTo(Integer value) {
            addCriterion("Use_userId <=", value, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridIn(List<Integer> values) {
            addCriterion("Use_userId in", values, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridNotIn(List<Integer> values) {
            addCriterion("Use_userId not in", values, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridBetween(Integer value1, Integer value2) {
            addCriterion("Use_userId between", value1, value2, "useUserid");
            return (Criteria) this;
        }

        public Criteria andUseUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("Use_userId not between", value1, value2, "useUserid");
            return (Criteria) this;
        }

        public Criteria andRolRightidIsNull() {
            addCriterion("rol_rightId is null");
            return (Criteria) this;
        }

        public Criteria andRolRightidIsNotNull() {
            addCriterion("rol_rightId is not null");
            return (Criteria) this;
        }

        public Criteria andRolRightidEqualTo(Integer value) {
            addCriterion("rol_rightId =", value, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidNotEqualTo(Integer value) {
            addCriterion("rol_rightId <>", value, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidGreaterThan(Integer value) {
            addCriterion("rol_rightId >", value, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidGreaterThanOrEqualTo(Integer value) {
            addCriterion("rol_rightId >=", value, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidLessThan(Integer value) {
            addCriterion("rol_rightId <", value, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidLessThanOrEqualTo(Integer value) {
            addCriterion("rol_rightId <=", value, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidIn(List<Integer> values) {
            addCriterion("rol_rightId in", values, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidNotIn(List<Integer> values) {
            addCriterion("rol_rightId not in", values, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidBetween(Integer value1, Integer value2) {
            addCriterion("rol_rightId between", value1, value2, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andRolRightidNotBetween(Integer value1, Integer value2) {
            addCriterion("rol_rightId not between", value1, value2, "rolRightid");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userId is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userId is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userId =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userId <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userId >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userId >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userId <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userId <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userId in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userId not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userId between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userId not between", value1, value2, "userid");
            return (Criteria) this;
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