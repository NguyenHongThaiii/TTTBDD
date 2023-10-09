package ecommerce.mobile.service;

import ecommerce.mobile.payload.StatisticsByDateDTO;

public interface StatisticsService {
	Double statisticsSalesByDate(StatisticsByDateDTO sbd);

//	Integer getCountUser(Integer companyId);
//
//	Integer getCountUser();
//
//	Integer getCountInvoiceIsPaid(Boolean isPaid);
}
