public class PrizeStructure {
    float group1Prize;
    float group2Prize;
    float group3Prize;
    float group4Prize;
    float group5Prize = 50;
    float group6Prize = 25;
    float group7Prize = 10;
    float userWinnings = 0;

    public void setPrize(float group2Statistic,float group3Statistic,float group4Statistic) {
        group2Prize = (group1Prize / 38 * 8)/group2Statistic;
        group3Prize = (group1Prize / 38 * 5.5F)/group3Statistic;
        group4Prize = (group1Prize / 38 * 3)/group4Statistic;
    }

    public void setGrp1Prize(float x) {
        group1Prize = x;
    }

    public void prizeChecker(boolean additionalNumberCheck, int counter, int userTotoSystem){
        //filter prize structure as each system pays out different even for same group prizes
        if (userTotoSystem == 6){
            if (counter == 3) {
                userWinnings += group7Prize;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += group6Prize;
            } else if (counter == 4) {
                userWinnings += group5Prize;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += group4Prize;
            } else if (counter == 5) {
                userWinnings += group3Prize;
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += group2Prize;
            } else if (counter == 6) {
                userWinnings += group1Prize;
            }
        } else if (userTotoSystem == 7) {
            if (counter == 3) {
                userWinnings += 40;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += 85;
            } else if (counter == 4) {
                userWinnings += 190;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += (group4Prize*2+150);
            } else if (counter == 5) {
                userWinnings += (group3Prize*2+250);
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += (group2Prize+group3Prize+group4Prize*5);
            } else if (counter == 6) {
                userWinnings += (group1Prize+group3Prize*6);
            } else if (counter == 6 && additionalNumberCheck) {
                userWinnings += (group1Prize + group2Prize * 6);
            }
        } else if (userTotoSystem == 8) {
            if (counter == 3) {
                userWinnings += 100;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += 190;
            } else if (counter == 4) {
                userWinnings += 460;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += (group4Prize*3+490);
            } else if (counter == 5) {
                userWinnings += (group3Prize*3+850);
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += (group2Prize+group3Prize*2+group4Prize*10+500);
            } else if (counter == 6) {
                userWinnings += (group1Prize+group3Prize*12+750);
            } else if (counter == 6 && additionalNumberCheck) {
                userWinnings += (group1Prize + group2Prize * 6+group3Prize*6+group4Prize*15);
            }
        } else if (userTotoSystem == 9) {
            if (counter == 3) {
                userWinnings += 200;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += 350;
            } else if (counter == 4) {
                userWinnings += 900;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += (group4Prize*4+1060);
            } else if (counter == 5) {
                userWinnings += (group3Prize*4+1900);
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += (group2Prize+group3Prize*3+group4Prize*15+1600);
            } else if (counter == 6) {
                userWinnings += (group1Prize+group3Prize*18+2450);
            } else if (counter == 6 && additionalNumberCheck) {
                userWinnings += (group1Prize + group2Prize * 6+group3Prize*12+group4Prize*30 + 1250);
            }
        } else if (userTotoSystem == 10) {
            if (counter == 3) {
                userWinnings += 350;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += 575;
            } else if (counter == 4) {
                userWinnings += 1550;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += (group4Prize*5+1900);
            } else if (counter == 5) {
                userWinnings += (group3Prize*5+3500);
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += (group2Prize+group3Prize*4+group4Prize*20+3400);
            } else if (counter == 6) {
                userWinnings += (group1Prize+group3Prize*24+5300);
            } else if (counter == 6 && additionalNumberCheck) {
                userWinnings += (group1Prize + group2Prize * 6+group3Prize*18+group4Prize*45 + 3950);
            }
        } else if (userTotoSystem == 11) {
            if (counter == 3) {
                userWinnings += 560;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += 875;
            } else if (counter == 4) {
                userWinnings += 2450;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += (group4Prize*6+3050);
            } else if (counter == 5) {
                userWinnings += (group3Prize*6+5750);
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += (group2Prize+group3Prize*5+group4Prize*25+6000);
            } else if (counter == 6) {
                userWinnings += (group1Prize+group3Prize*30+9500);
            } else if (counter == 6 && additionalNumberCheck) {
                userWinnings += (group1Prize + group2Prize * 6+group3Prize*24+group4Prize*60 + 8300);
            }
        } else if (userTotoSystem == 12) {
            if (counter == 3) {
                userWinnings += 840;
            } else if (counter == 3 && additionalNumberCheck) {
                userWinnings += 1260;
            } else if (counter == 4) {
                userWinnings += 3640;
            } else if (counter == 4 && additionalNumberCheck) {
                userWinnings += (group4Prize*7+4550);
            } else if (counter == 5) {
                userWinnings += (group3Prize*7+8750);
            } else if (counter == 5 && additionalNumberCheck) {
                userWinnings += (group2Prize+group3Prize*6+group4Prize*30+9500);
            } else if (counter == 6) {
                userWinnings += (group1Prize+group3Prize*36+15250);
            } else if (counter == 6 && additionalNumberCheck) {
                userWinnings += (group1Prize + group2Prize * 6+group3Prize*30+group4Prize*75 + 14500);
            }
        }

    }
}
