package com.gulaev.service.google;

import com.gulaev.entity.SheetInfo;
import java.util.HashMap;
import java.util.Map;

public class SheetMapper {

  public static final Map<String, SheetInfo> kivalsMapping = new HashMap<>();
  public static final Map<String, SheetInfo> zoromsMapping = new HashMap<>();
  public static final Map<String, SheetInfo> migthyXUKMapping = new HashMap<>();
  public static final Map<String, SheetInfo> migthyXUSMapping = new HashMap<>();

  static {
    // ZOROM'S
    zoromsMapping.put("B09R4WL9WP", new SheetInfo("1uY6Wjf51eV3rxf7zJI3t1NwHfI9NIbcQdXaYg3Bh-9M", "Daily Data Input"));
    zoromsMapping.put("B086692GCS", new SheetInfo("10j-CApDnnFqemz2x93zmRYmKRKHESjofCwQrqwKYPLo", "Daily Data Input"));
    zoromsMapping.put("B08X1XZC14", new SheetInfo("1idPKfa498qKxihwLihOrrSxVTMyM_volDsIiEAHROK4", "Daily Data Input"));
    zoromsMapping.put("B09R4WV42F", new SheetInfo("1YGqWf1sQ0ZkXRWJ2HOHOdYfickLj6RLe8d9qMwPzlts", "Daily Data Input"));
    zoromsMapping.put("B08X1YFHYJ", new SheetInfo("11aa6YBXzh-lSl4oca6oBkAuRKjk5AmZH2f6rw6ZKSds", "Daily Data Input"));
    zoromsMapping.put("B08X17XN1T", new SheetInfo("14zvNWgNX7Lkpvr8Ym-sC2qL6RCyDt7kxGgyoEFAFKOQ", "Daily Data Input"));
    zoromsMapping.put("B0895NXVPQ", new SheetInfo("1Pwx2edsNiltyXXtXF5nPiHXI0aIf8HqB_mTpLQZj7nI", "Daily Data Input"));
    zoromsMapping.put("B08X15TN31", new SheetInfo("1FNW-gad2xaQV-50wItcy__L01LNrfdwVC0T_bz0GLBk", "Daily Data Input"));

    // Mighty-X UK
    migthyXUKMapping.put("B07GB8TB9T", new SheetInfo("1XCj4nm3aOVUPPPNZxmqhMz1N1QgHX0ripg4hjBa6dQ8", "Daily Data Input"));
    migthyXUKMapping.put("B078Q32FXQ", new SheetInfo("1cwHBYxZ6hiVvzqS2boeczqS45btBRB93p6Tzg7JN_dk", "Daily Data Input"));
    migthyXUKMapping.put("B078Q7WJSG", new SheetInfo("1-W__qZBGE-0mmA6tPZFRjXhQtOKqzufXO2PGdplX_ls", "Daily Data Input"));
    migthyXUKMapping.put("B07MXRL76N", new SheetInfo("1n6PhnRZwiAxzYEvbvs__wpxxkjiHpWP6rUfVsgjJFew", "Daily Data Input"));
    migthyXUKMapping.put("B07R2D594Z", new SheetInfo("1OIXTHu0IMAugSEZ1bt74YxkWyEyVYsDGrrv4gUr8rrQ", "Daily Data Input"));
    migthyXUKMapping.put("B08J3ZRTLQ", new SheetInfo("16JMwAfZn6SsNHWmBsPuZjhI-0cvo67NhbKp3EL02Vgs", "Daily Data Input"));
    migthyXUKMapping.put("B08J4G25JZ", new SheetInfo("11eEvJsRzSiFf_pBDtPkVBO20ptcbFkOov4iPRbtIXmU", "Daily Data Input"));
    migthyXUKMapping.put("B08HZ94M16", new SheetInfo("1bnc4sE_ZcSAthGw12zrhgUT0hSI62X1IM9R3iDiT_50", "Daily Data Input"));
    migthyXUKMapping.put("B07HHL3W1M", new SheetInfo("1sB-bTdPdXgSeyRhO0jww-7qBjENzBtERrKrFaHypX64", "Daily Data Input"));

    // Mighty-X US
    migthyXUSMapping.put("B06XQZ12NH", new SheetInfo("1ElM1jCxYTUQe2SGgjkxIsQaoPpdm-plC7u3hSIbWS00", "Daily Data Input"));
    migthyXUSMapping.put("B01HQWDQ1E", new SheetInfo("1N8gGpC-moYsAtpIABIW01RCr1SY9l_HYL0zlUdhYad4", "Daily Data Input"));
    migthyXUSMapping.put("B071H9RYJX", new SheetInfo("1cpE4Ou3EigIY3UT9Rbn2QFhnr0bpuCwXIygMfwcZo9Y", "Daily Data Input"));
    migthyXUSMapping.put("B07TW7STZY", new SheetInfo("1_432TJh02r1rtvGnbDGTITj3JxFt2eHckVliv04DbKg", "Daily Data Input"));
    migthyXUSMapping.put("B08HZ5SMF4", new SheetInfo("1m1Z1XrnIN4yCGASTty5ZD6LgxCVPoaLicDI7Ce2rfds", "Daily Data Input"));
    migthyXUSMapping.put("B0BN8K48JH", new SheetInfo("1oqJ_3nd3XjnnY5rKWlpZ1LG58eG2vwwpgB3I-AZ4rqw", "Daily Data Input"));
    migthyXUSMapping.put("B0BNJ9FW5B", new SheetInfo("1J6mJlAHZrMWx0yNyNDhrlhaHuLdIDZkuPc9cKLsHxeM", "Daily Data Input"));
    migthyXUSMapping.put("B0BPMMSQ13", new SheetInfo("1CVkLP087l99FuGJV4t_8UxWqq8LAowAs6LlEpXr5yrw", "Daily Data Input"));
    migthyXUSMapping.put("B07DXCGCHF", new SheetInfo("1e_eBA1g-KYdqI4rsxZeNQ0xGMf4m60CtcZ3qWJkOR1I", "Daily Data Input"));
    migthyXUSMapping.put("B06XQ8B8Y8", new SheetInfo("17KFHwGTBc9lJ77CuEanCRARTHU4WQJLpdyUuC2lZPis", "Daily Data Input"));
    migthyXUSMapping.put("B07R2D594Z", new SheetInfo("1xukL2Dmox9D14O5nzaPfspvwMyv8ayohsuo_jvF9wvA", "Daily Data Input"));
    migthyXUSMapping.put("B07XJPY5PZ", new SheetInfo("1oaXpjeRqq1xXETsDx7e3rjtOO_DHoZkZWg-LWTkfQvc", "Daily Data Input"));
    migthyXUSMapping.put("B076ZVLMYR", new SheetInfo("1j6ViCz-XyLir16Ge2IUxiXOAveFK1ZfPjbj6qRUVPNg", "Daily Data Input"));
    migthyXUSMapping.put("B07R17G8PR", new SheetInfo("1AxiiAZjR5M_zrHFXD8XRQw0gemEYXhORfFbhzVNyAtY", "Daily Data Input"));
    migthyXUSMapping.put("B08HZ94M16", new SheetInfo("1LS6y8Qnaq2WOogeLAJKr_FO3-4h1WIW7_-BFZYknsUQ", "Daily Data Input"));
    migthyXUSMapping.put("B08J3VBG3Y", new SheetInfo("1lImPriw1q0NncBFG3C0PVKFNn_pOgdt0bxqmO_7167c", "Daily Data Input"));
    migthyXUSMapping.put("B099S5CF9W", new SheetInfo("1mz-QGMMHgpdlur0YdBuu_ubV7QtE3aIWYv0wQJFYWoA", "Daily Data Input"));
    migthyXUSMapping.put("B099S6CZLT", new SheetInfo("1nl2x5dT2P703kN7PtdFO1vke8PorTySFdjwUonb5RJE", "Daily Data Input"));
    migthyXUSMapping.put("B078Q32FXQ", new SheetInfo("1Olib7UYpI0zO3-En9sKQ4sZeWyrbxQEWCzUwazr-Jvw", "Daily Data Input"));
    migthyXUSMapping.put("B078Q7WJSG", new SheetInfo("167gPOZ69MlksFm6E6Mc5Wnh3wNmCobpVAjpyA4OyIq4", "Daily Data Input"));
    migthyXUSMapping.put("B08J4G25JZ", new SheetInfo("14N6NcN4FqrIeCUU5-Q0Lv-9tTGV0Pi5nZbuM48cb2_0", "Daily Data Input"));
    migthyXUSMapping.put("B08J3ZRTLQ", new SheetInfo("14YPNIibBtbZnFTh8LMqP_tfY1ZE1_f-fAu_MWhyhTC0", "Daily Data Input"));
    migthyXUSMapping.put("B07HHL3W1M", new SheetInfo("1DNI5I2GeTnRpnl_scd8M0RpAYGpe6oR9ulknFGsLqKc", "Daily Data Input"));
    migthyXUSMapping.put("B09R83Z3JQ", new SheetInfo("1OofTd0aRJvqoWU-h91CTUBXL9fDnJ6PKsvg5PSbPKus", "Daily Data Input"));
    migthyXUSMapping.put("B09R851CL3", new SheetInfo("1nV3hTvaLZs9E9oodYcxUsWOS0WS_GUGqhxssaCNYXzc", "Daily Data Input"));
    migthyXUSMapping.put("B07JLMKJGX", new SheetInfo("12uvXodfyftLIQko7LjdnWgoMX_ND8qohY6mpr5PtgZY", "Daily Data Input"));
    migthyXUSMapping.put("B09R8355XC", new SheetInfo("1OuSmhgQ5xXYBVEIHsjv0zZx4NbnldDijDg86OAJ6AdE", "Daily Data Input"));
    migthyXUSMapping.put("B07BJB629Z", new SheetInfo("1EfQ_QMdgf1elg9j2srZislcO9zjgXhwC4lTk9Af_TFI", "Daily Data Input"));
    migthyXUSMapping.put("B07BJ9K2WT", new SheetInfo("1ehQeKslwBgM2LxTm90tTFD2EjEw7hEkJlF0JpjoUX3Y", "Daily Data Input"));
    migthyXUSMapping.put("B07BJ9VK6L", new SheetInfo("1NBIZ8wh8RShCIZZFe_W5yUZMfPMFsm1qiQqezKSZUng", "Daily Data Input"));
    migthyXUSMapping.put("B07BHN9TFY", new SheetInfo("13jcKa1oYWreODh6mFkz706PgxKn1T4arHOe_GK1LvLY", "Daily Data Input"));


    // Kivals
    kivalsMapping.put("B0C2D8MQS3", new SheetInfo("1ysAk27L93cU2Qn8hGeiSXdHvMvdabps3YVwuGmAnTBw", "Daily Data Input"));
    kivalsMapping.put("B0C1ZZ766Q", new SheetInfo("1Quf8VAfHQpDQW9fEpi61koDtLLDXlkgZQ7y-XKZjbaw", "Daily Data Input"));
    kivalsMapping.put("B0C2D7JYQT", new SheetInfo("1O4glB5p36ShbBGY3rZS1VUDe13RrLPbSPNdAbuoGxPc", "Daily Data Input"));
    kivalsMapping.put("B0C1T5RNCH", new SheetInfo("1pIWuwqOPWDqmO3MUqxlpDQm1mhBYKX_zvbgRx4KZtHM", "Daily Data Input"));

  }

  public static Map<String, SheetInfo> getMigthyXUKMapping() {
    return migthyXUKMapping;
  }

  public static Map<String, SheetInfo> getMigthyXUSMapping() {
    return migthyXUSMapping;
  }

  public static Map<String, SheetInfo> getKivalsMapping() {
    return kivalsMapping;
  }

  public static Map<String, SheetInfo> getZoromsMapping() {
    return zoromsMapping;
  }
}
