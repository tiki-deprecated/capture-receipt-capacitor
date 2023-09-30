import type { Account } from 'src/account';
import type { CallbackDataError, Receipt } from 'src/types';

export type Callback = (payload?: Account | Receipt | CallbackDataError) => void