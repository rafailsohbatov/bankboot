package az.mycompany.bankboot.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EnumAvailableStatus {
    ACTIVE(1),DEACTIVE(0);

    private Integer value;

    public Integer getValue() {
        return value;
    }
}
