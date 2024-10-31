package lotto.controller;

import lotto.model.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;
import java.util.Map;

public class LottoController {
    private static final int LOTTO_PRICE = 1_000;

    public void run() {
        int money = InputView.getMoney();
        int numberOfLottos = calculateNumberOfLottos(money);
        List<Lotto> purchasedLottos = LottoGenerator.generateLottos(numberOfLottos);
        OutputView.printPurchasedLottos(purchasedLottos);

        WinningLotto winningLotto = new WinningLotto(InputView.getWinningNumbers(), InputView.getBonusNumber());
        LottoResult result = new LottoResult(winningLotto, purchasedLottos);
    }

    private int calculateNumberOfLottos(int money) {
        if (money % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 로또 구입 금액은 1,000원 단위여야 합니다.");
        }
        return money / LOTTO_PRICE;
    }

    private double calculateRateOfReturn(LottoResult result, int money) {
        Map<Rank, Integer> resultMap = result.getResultCountMap();
        double totalPrize = 0;

        for (Rank rank : resultMap.keySet()) {
            int count = resultMap.get(rank);
            totalPrize += rank.getPrize() * count;
        }

        double rateOfReturn = (totalPrize / money) * 100;
        rateOfReturn = Math.round(rateOfReturn * 10) / 10.0; // 소수점 둘째 자리에서 반올림

        return rateOfReturn;
    }
}