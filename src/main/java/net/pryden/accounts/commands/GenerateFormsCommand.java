package net.pryden.accounts.commands;

import net.pryden.accounts.Storage;
import net.pryden.accounts.commands.Annotations.CurrentMonth;
import net.pryden.accounts.model.AccountsMonth;
import net.pryden.accounts.reports.AccountsSheetForm;
import net.pryden.accounts.reports.BranchTransferForm;

import javax.inject.Inject;
import java.io.IOException;
import java.time.YearMonth;

final class GenerateFormsCommand implements Command {
  private final Storage storage;
  private final YearMonth currentMonth;
  private final AccountsSheetForm accountsSheetForm;
  private final BranchTransferForm branchTransferForm;

  @Inject
  GenerateFormsCommand(
      Storage storage,
      @CurrentMonth YearMonth currentMonth,
      AccountsSheetForm accountsSheetForm,
      BranchTransferForm branchTransferForm) {
    this.storage = storage;
    this.currentMonth = currentMonth;
    this.accountsSheetForm = accountsSheetForm;
    this.branchTransferForm = branchTransferForm;
  }

  @Override
  public void run() throws IOException {
    AccountsMonth month = storage.readMonth(currentMonth);
    accountsSheetForm.generate(month);
    if (month.isClosed()) {
      branchTransferForm.generate(month);
    }
  }
}
