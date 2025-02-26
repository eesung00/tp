package seedu.application.logic.parser;

import static seedu.application.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.application.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.application.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.application.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.application.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.application.logic.parser.CliSyntax.PREFIX_POSITION;

import java.util.stream.Stream;

import seedu.application.logic.commands.AddCommand;
import seedu.application.logic.parser.exceptions.ParseException;
import seedu.application.model.application.Application;
import seedu.application.model.application.Company;
import seedu.application.model.application.Contact;
import seedu.application.model.application.Date;
import seedu.application.model.application.Email;
import seedu.application.model.application.Position;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_CONTACT, PREFIX_EMAIL,
                PREFIX_POSITION, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMPANY, PREFIX_CONTACT, PREFIX_EMAIL, PREFIX_POSITION, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        }

        Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        Contact contact = ParserUtil.parseContact(argMultimap.getValue(PREFIX_CONTACT).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Position position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        Application application = new Application(company, contact, email, position, date);

        return new AddCommand(application);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
