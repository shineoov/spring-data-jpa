## 프록시

### 특징
- 프록시 객체는 처음 사용할 때 한 번만 초기화된다.
- 프록시 객체를 초기화한다고 프록시 객체가 실제 엔티티로 바뀌는 것은 아니다. 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근할 수 있다.
- 프록시 객체는 원본 엔티티를 상속받은 객체이므로 타입 체크 시에 주의해서 사용해야한다.
- 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 데이터베이스를 조회할 필요가 없으므로 em.getReference() 을 호출해도 프록시가 아닌 실제 엔티티를 반환한다.
- 초기화는 영속성 컨텍스트의 도움을 받아야 가능하다. 따라서 영속성 컨텍스트의 도음을 받을 수 없는 준영속 상태의 프록시를 초기화하면 문제가 발생한다.

### 즉시로딩
> 설정 방법: @ManyToOne(fetch = FetchType.EAGER)
> 연관된 엔티티를 즉시 조회한다.
> 엔티티를 조회할 때 연관된 엔티티도 함께 조회한다.

#### JPA 조인 전략 과 NULL 제약조건
- @JoinColumn(nullable = false) -> INNER JOIN
- @JoinColumn(nullable = true) -> LEFT OUTER JOIN
- @ManyToOne(optional = false) -> INNER JOIN  

*JPA 는 선택적 관계면 외부 조인을 사용하고 필수 관계면 내부 조인을 사용한다.*

### 지연 로딩
> 설정 방법: @ManyToOne(fetch = FetchType.LAZY)
> 연관된 엔티티를 프록시로 조회한다.
> 연관된 엔티티를 실제 사용할 때 초기화하면서 데이터베이서를 조회한다.


