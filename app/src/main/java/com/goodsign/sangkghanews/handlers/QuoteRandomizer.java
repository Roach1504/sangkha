package com.goodsign.sangkghanews.handlers;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Roman on 23.02.2017.
 */

public class QuoteRandomizer
{
    private ArrayList<String> quotes;
    private int lastQuoteNumber;
    private Random random;

    public QuoteRandomizer()
    {
        random = new Random();
        lastQuoteNumber = -1;
        quotes = new ArrayList<>(6);
        quotes.add("Да пребудет с вами Будда");
        quotes.add("Пусть вам сопутствует удача");
        quotes.add("Пусть вас защищают все боги");
        quotes.add("Улыбнитесь, всё будет хорошо");
        quotes.add("Будьте сильны духом");
        quotes.add("Будьте в добром здравии");
    }

    public String getRandomQuote()
    {
        if (lastQuoteNumber != -1)
        {
            int a = random.nextInt(4);
            if (a < 3)
            {
                return quotes.get(lastQuoteNumber);
            }
            else
            {
                int b = random.nextInt(quotes.size());
                return quotes.get(b);
            }
        }
        else
        {
            int b = random.nextInt(quotes.size());
            lastQuoteNumber = b;
            return quotes.get(b);
        }

    }

}
